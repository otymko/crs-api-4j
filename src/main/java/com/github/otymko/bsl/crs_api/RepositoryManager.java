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

import com.github.otymko.bsl.crs_api.util.HashingPassword;
import com.github.otymko.bsl.crs_api.util.RequestBodyHelper;
import com.github.otymko.bsl.crs_api.util.RequestManager;

/**
 * Действия над хранилищем конфигураций
 */
public class RepositoryManager {

  private RepositoryManager() {
    // none
  }

  /**
   * Создать новое хранилище на сервере
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

}
