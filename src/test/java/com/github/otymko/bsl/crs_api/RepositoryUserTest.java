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

import com.github.otymko.bsl.crs_api.format.User;
import com.github.otymko.bsl.crs_api.format.info.UserInfo;
import com.github.otymko.bsl.crs_api.util.Common;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryUserTest {

  @Test
  void testFillByUserInfo() {
    var userInfo = new UserInfo("f02fb7e4-8477-11eb-c986-0242ac150002", "Администратор",
      "06d49632c9dc9bcb62aeaef99612ba6b", "65535");
    var user = new User(userInfo, true, false);

    var repositoryUser = Common.createRepositoryUserByInfo(user);
    assertThat(repositoryUser.isOnline()).isTrue();
    assertThat(repositoryUser.isRemoved()).isFalse();
    assertThat(repositoryUser.getId()).hasToString("f02fb7e4-8477-11eb-c986-0242ac150002");
    assertThat(repositoryUser.getName()).hasToString("Администратор");
    assertThat(repositoryUser.getPasswordHash()).hasToString("06d49632c9dc9bcb62aeaef99612ba6b");
    assertThat(repositoryUser.getRole().value()).hasToString("65535");
  }

}