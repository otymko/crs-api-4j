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

/**
 * Права (роль) пользователя в хранилище
 */
public enum UserRole {
  /**
   * Полные права
   */
  ADMIN("65535"),
  /**
   * Только захват объектов
   */
  DEVELOPER("1"),
  /**
   * Захват объектов и изменение состава версий
   */
  DEVELOPER_EXTENDED("5"),
  /**
   * Только просмотр
   */
  VIEW_ONLY("0");

  String value;

  UserRole(String value) {
    this.value = value;
  }

  /**
   * Получить текстовое представление роли
   *
   * @return текстовое представление
   */
  public String value() {
    return value;
  }

  /**
   * Получить роль по текстовому представлению
   *
   * @param value текстовое представление
   * @return роль
   */
  public static UserRole fromValue(String value) {
    for (var userRole : UserRole.values()) {
      if (userRole.value().equals(value)) {
        return userRole;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
