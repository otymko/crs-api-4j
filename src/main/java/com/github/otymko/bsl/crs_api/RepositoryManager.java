/*
 * This file is a part of CRS-API for java.
 *
 * Copyright © 2021
 * Tymko Oleg <olegtymko@yandex.ru> and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.otymko.bsl.crs_api;

import com.github.otymko.bsl.crs_api.format.CallExceptionResponse;
import com.github.otymko.bsl.crs_api.util.Common;
import com.github.otymko.bsl.crs_api.util.ErrorMessageConvertor;
import com.github.otymko.bsl.crs_api.util.HashingPassword;
import com.github.otymko.bsl.crs_api.util.RequestBodyHelper;
import com.github.otymko.bsl.crs_api.util.RequestManager;

import java.util.UUID;

/**
 * Действия над сервером хранилища конфигураций
 */
public class RepositoryManager {
  private static final String EMPTY_PLATFORM_VERSION = "0.0.0.0";

  private RepositoryManager() {
    // none
  }

  /**
   * Создать новый репозиторий на сервере
   *
   * @param url             адрес сервера хранилища конфигураций. Например, http://localhost/repo.1ccr
   * @param repository      имя репозитория
   * @param platformVersion версия платформы 1С
   * @param user            пользователь
   * @param password        пароль пользователя
   * @throws RepositoryClientException
   */
  public static void createRepository(String url, String repository, String platformVersion, String user,
                                      String password) throws RepositoryClientException {
    var passwordHash = HashingPassword.hash(password);
    var xml = RequestBodyHelper.createDevDepot(repository, platformVersion, user, passwordHash,
      RequestBodyHelper.TEMPLATE_CREATE_DEPOT);
    RequestManager.getRequestResult(url, xml);
  }

  /**
   * Проверить существование репозитория на сервере
   *
   * @param url             адрес сервера хранилища конфигураций. Например, http://localhost/repo.1ccr
   * @param repository      имя репозитория
   * @param platformVersion версия платформы 1С
   * @return признак существования репозитория
   */
  public static boolean repositoryExist(String url, String repository, String platformVersion) {
    boolean exist;
    var xml = RequestBodyHelper.openDevDepot(repository, platformVersion);
    try {
      RequestManager.getRequestResult(url, xml);
      exist = true;
    } catch (RepositoryClientException exception) {
      exist = false;
    }
    return exist;
  }

  /**
   * Получить версию платформы 1С на сервере
   *
   * @param url адрес сервера хранилища конфигураций. Например, http://localhost/repo.1ccr
   * @return версия платформы. Например, 8.3.12.1855
   * @throws RepositoryClientException
   */
  public static String getPlatformVersion(String url) throws RepositoryClientException {
    String platformVersion;
    var xml = RequestBodyHelper.openDevDepot(UUID.randomUUID().toString(), EMPTY_PLATFORM_VERSION);
    var result = RequestManager.getRequestResultWithSupportCallException(url, xml);
    if (result instanceof CallExceptionResponse) {
      var callException = (CallExceptionResponse) result;
      var message = ErrorMessageConvertor.decodeMessage(callException.getMessage());
      platformVersion = Common.getPlatformVersionFromErrorMessage(message);
    } else {
      throw new RepositoryClientException("Не удалось получить версию платформы на сервере");
    }
    return platformVersion;
  }

}
