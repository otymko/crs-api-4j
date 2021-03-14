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
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@UtilityClass
public class HashingPassword {

  /**
   * Хэшировать строку по алгоритму: исходное значение -> UTF_16LE -> MD5.
   *
   * @param value произвольная строка
   * @return хэшированная строка
   */
  public String hash(String value) {
    var bytes = value.getBytes(StandardCharsets.UTF_16LE);
    var result = DigestUtils.md5Hex(bytes);
    return result.toLowerCase(Locale.ENGLISH);
  }

}
