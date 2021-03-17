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

import com.github.otymko.bsl.crs_api.format.CallReturnResponse;
import com.github.otymko.bsl.crs_api.util.Common;
import com.github.otymko.bsl.crs_api.util.HashingPassword;
import com.github.otymko.bsl.crs_api.util.RequestBodyHelper;
import com.github.otymko.bsl.crs_api.util.RequestManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Клиент хранилища конфигурации
 */
public class RepositoryClient {
  public static final String DEFAULT_REPOSITORY_NAME = "maincr";
  /**
   * Адрес сервера хранилища конфигураций. Например:
   * http://localhost/repo.1ccr
   */
  private final String url;
  /**
   * Версия платформы 1С. Например, 8.3.12.1855
   */
  private final String platformVersion;
  /**
   * Имя хранилища на сервере. Например, `maincr`
   */
  private String repository;
  /**
   * Имя пользователя хранилища для подключения
   */
  private String user;
  /**
   * Пароль пользователя хранилища в хэшированном виде {@link HashingPassword#hash}
   */
  private String passwordHash;
  /**
   * Признак установленного соединения с сервером хранилища
   */
  private boolean connectionEstablished = false;

  /**
   * Новый экземпляр объекта
   *
   * @param url             адрес сервера хранилища конфигураций. Например, http://localhost/repo.1ccr
   * @param platformVersion версия платформы 1С. Например, 8.3.12.1855
   */
  public RepositoryClient(String url, String platformVersion) {
    this.url = url;
    this.platformVersion = platformVersion;
  }

  /**
   * Подключиться к хранилищу конфигурации
   *
   * @param user       пользователь хранилища
   * @param password   пароль пользователя хранилища
   * @param repository имя хранилища
   * @throws RepositoryClientException
   */
  public void connect(String user, String password, String repository) throws RepositoryClientException {
    connectionEstablished = false;
    var hash = HashingPassword.hash(password);
    var xml = RequestBodyHelper.depotInfo(repository, platformVersion, user, hash);
    RequestManager.getRequestResult(url, xml);
    this.connectionEstablished = true;
    this.repository = repository;
    this.user = user;
    this.passwordHash = hash;
  }

  /**
   * Подключиться к хранилищу конфигурации
   *
   * @param user     - пользователь хранилища
   * @param password - пароль пользователя хранилища
   * @throws RepositoryClientException
   */
  public void connect(String user, String password) throws RepositoryClientException {
    connect(user, password, DEFAULT_REPOSITORY_NAME);
  }

  /**
   * Создать нового пользователя хранилища
   *
   * @param userName     имя пользователя
   * @param userPassword пароль (в открытом виде)
   * @param role         роль пользователя
   * @throws RepositoryClientException
   */
  public void createUser(String userName, String userPassword, UserRole role) throws RepositoryClientException {
    checkConnection();
    var hash = HashingPassword.hash(userPassword);
    var xml = RequestBodyHelper.addUser(repository, platformVersion, user, passwordHash, userName, hash,
      role.value());
    RequestManager.getRequestResult(url, xml);
  }

  /**
   * Получить пользователя хранилища
   *
   * @param userName имя пользователя
   * @return пользователь хранилища
   * @throws RepositoryClientException
   */
  public RepositoryUser getUser(String userName) throws RepositoryClientException {
    checkConnection();
    var xml = RequestBodyHelper.depotUserByName(repository, platformVersion, user, passwordHash, userName);
    var requestResult = RequestManager.getRequestResult(url, xml);
    var callReturned = (CallReturnResponse) requestResult;
    return Common.createRepositoryUserByInfo(callReturned.getUser());
  }

  /**
   * Деактивировать пользователя хранилища. Для получения идентификатора пользователя, нужно
   * использовать метод {@link #getUser}.
   *
   * @param userId идентификатор пользователя. Например, "b9dd0f2c-5ed0-484a-fg12-56494aa67301".
   * @throws RepositoryClientException обработанная ошибка по взаимодействию с сервером
   */
  public void removeUser(String userId) throws RepositoryClientException {
    checkConnection();
    var xml = RequestBodyHelper.removeUser(repository, platformVersion, user, passwordHash, userId);
    RequestManager.getRequestResult(url, xml);
  }

  /**
   * Восстановить пользователя хранилища. Для получения идентификатора пользователя, нужно
   * использовать метод {@link #getUser}.
   *
   * @param userId идентификатор пользователя. Например, "b9dd0f2c-5ed0-484a-fg12-56494aa67301".
   * @throws RepositoryClientException обработанная ошибка по взаимодействию с сервером
   */
  public void resurrectUser(String userId) throws RepositoryClientException {
    checkConnection();
    var xml = RequestBodyHelper.resurrectUser(repository, platformVersion, user, passwordHash, userId);
    RequestManager.getRequestResult(url, xml);
  }

  /**
   * Получить список пользователей хранилища
   *
   * @return список пользователей хранилища
   * @throws RepositoryClientException
   */
  public List<RepositoryUser> getUsers() throws RepositoryClientException {
    checkConnection();
    var xml = RequestBodyHelper.devObjectsStatistic(repository, platformVersion, user, passwordHash);
    var requestResult = RequestManager.getRequestResult(url, xml);
    var callReturned = (CallReturnResponse) requestResult;
    return callReturned.getUsers().getCRSValues().stream()
      .map(Common::createRepositoryUserByCRSValue)
      .collect(Collectors.toList());
  }

  private void checkConnection() throws RepositoryClientException {
    if (!connectionEstablished) {
      throw new RepositoryClientException("Соединение не установлено");
    }
  }

}
