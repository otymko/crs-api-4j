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
package com.github.otymko.bsl.crs_api.format.info;

import com.github.otymko.bsl.crs_api.xstream.CRSValueConverter;
import com.github.otymko.bsl.crs_api.xstream.DateValueConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Value;

import java.util.Date;

@Value
public class CRSInfo {
  @XStreamAlias("crs:info")
  CRSInfo CRSInfo;

  @XStreamAlias("crs:date")
  @XStreamConverter(value = DateValueConverter.class)
  Date date;

  @XStreamAlias("crs:isExtension")
  @XStreamConverter(value = CRSValueConverter.class)
  boolean extension;

  @XStreamAlias("crs:depotID")
  @XStreamConverter(value = CRSValueConverter.class)
  String depotID;

  @XStreamAlias("crs:rootID")
  @XStreamConverter(value = CRSValueConverter.class)
  String rootID;

  @XStreamAlias("crs:mode")
  @XStreamConverter(value = CRSValueConverter.class)
  String mode;
}