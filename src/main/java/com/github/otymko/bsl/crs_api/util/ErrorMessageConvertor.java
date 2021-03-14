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

import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class ErrorMessageConvertor {

  /**
   * Декодировать сообщение от сервера хранилищ. При неудачном декодировании возвращается пустая строка.
   *
   * @param message сообщение от сервера хранилищ в формате Base64
   * @return декодированная строка
   */
  public String decodeMessage(String message) {
    byte[] decodedBytes;
    try {
      decodedBytes = Base64.getDecoder().decode(message.replace("\n", ""));
    } catch (IllegalArgumentException exception) {
      decodedBytes = new byte[0];
    }
    return new String(decodedBytes);
  }

}
