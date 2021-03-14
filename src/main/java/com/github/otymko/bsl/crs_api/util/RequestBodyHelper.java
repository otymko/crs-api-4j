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

import java.util.UUID;

// FIXME: переделать на правильное создание xml сообщений к релизу 0.3.0
@UtilityClass
public class RequestBodyHelper {

  public String depotInfo(String repository, String platformVersion, String user, String passwordHash) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"DevDepot_depotInfo\" " +
      "version=\"" + platformVersion + "\"> " +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\"/> " +
      "<crs:params/> " +
      "</crs:call>";
  }

  public String addUser(String repository, String platformVersion, String user, String passwordHash,
                        String userName, String hash, String role) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"UserManager_addUser\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\" />" +
      "<crs:params>" +
      "<crs:user>" +
      "<crs:id value=\"" + UUID.randomUUID().toString() + "\" />" +
      "<crs:name value=\"" + userName + "\" />" +
      "<crs:password value=\"" + hash + "\" />" +
      "<crs:rights value=\"" + role + "\" />" +
      "</crs:user>" +
      "</crs:params>" +
      "</crs:call>";
  }

  public String depotUserByName(String repository, String platformVersion, String user, String passwordHash,
                                String userName) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"UserManager_depotUserByName\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\" />" +
      "<crs:params>" +
      "<crs:name value=\"" + userName + "\" />" +
      "</crs:params>" +
      "</crs:call>";
  }

  public String devObjectsStatistic(String repository, String platformVersion, String user, String passwordHash) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"DevDepot_devObjectsStatistic\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\"/>" +
      "<crs:params>" +
      "<crs:objRefs/>" +
      "<crs:removed value=\"true\"/>" +
      "</crs:params>" +
      "</crs:call>";
  }
}
