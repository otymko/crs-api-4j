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
package com.github.otymko.bsl.crs_api.format;

import com.github.otymko.bsl.crs_api.format.info.UserInfo;
import com.github.otymko.bsl.crs_api.xstream.CRSValueConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Value;

@Value
public class User {
  @XStreamAlias("crs:info")
  UserInfo info;

  @XStreamAlias("crs:online")
  @XStreamConverter(value = CRSValueConverter.class)
  boolean online;

  @XStreamAlias("crs:removed")
  @XStreamConverter(value = CRSValueConverter.class)
  boolean removed;
}
