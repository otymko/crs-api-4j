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
package com.github.otymko.bsl.crs_api.util;

import com.github.otymko.bsl.crs_api.RepositoryUser;
import com.github.otymko.bsl.crs_api.format.CRSValue;
import com.github.otymko.bsl.crs_api.format.User;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * Утилитный класс общего назначения
 */
@UtilityClass
public class Common {
  private static final Pattern SEARCH_PLATFORM =
    Pattern.compile("[1-9]\\.\\d\\.\\d{1,3}\\.\\d{1,5}", Pattern.MULTILINE);

  /**
   * Создать пользователя хранилища по данным из CRSValue
   *
   * @param value CRSValue, полученный при чтении списка пользователей
   * @return пользователь хранилища
   */
  public RepositoryUser createRepositoryUserByCRSValue(CRSValue value) {
    return createRepositoryUserByInfo(value.getUser());
  }

  /**
   * Создать пользователя хранилища по данным из User
   *
   * @param info User, полученный при чтении пользователя
   * @return пользователь хранилища
   */
  public RepositoryUser createRepositoryUserByInfo(User info) {
    var repositoryUser = new RepositoryUser();
    repositoryUser.setId(info.getInfo().getId());
    repositoryUser.setName(info.getInfo().getName());
    repositoryUser.setPasswordHash(info.getInfo().getPassword());
    repositoryUser.setRole(info.getInfo().getRights());
    repositoryUser.setOnline(info.isOnline());
    repositoryUser.setRemoved(info.isRemoved());
    return repositoryUser;
  }

  /**
   * Получить из текста ошибки версию платформы 1С
   *
   * @param message текст ошибки
   * @return версия платформы 1С
   */
  public String getPlatformVersionFromErrorMessage(String message) {
    var matcher = SEARCH_PLATFORM.matcher(message);
    if (matcher.find()) {
      return matcher.group(0);
    }
    return "";
  }

}
