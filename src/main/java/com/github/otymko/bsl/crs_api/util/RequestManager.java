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

import com.github.otymko.bsl.crs_api.RepositoryClientException;
import com.github.otymko.bsl.crs_api.format.CallExceptionResponse;
import com.github.otymko.bsl.crs_api.xstream.XStreamHelper;
import lombok.experimental.UtilityClass;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

@UtilityClass
public class RequestManager {
  private final MediaType XML_MEDIA_TYPE = MediaType.get("application/xml; charset=utf-8");

  /**
   * Получить обработанный ответ сервера хранилищ
   *
   * @param url адрес сервера хранилищ
   * @param xml xml сообщение
   * @return десериализованный ответ сервера хранилищ
   * @throws RepositoryClientException сервер хранилищ вернул обработанную ошибку
   */
  public Object getRequestResult(String url, String xml) throws RepositoryClientException {
    var client = new OkHttpClient();
    var request = RequestManager.createRequest(url, xml);
    var responseBody = RequestManager.getResponseBody(client, request);
    var result = XStreamHelper.readFromString(responseBody);
    if (result instanceof CallExceptionResponse) {
      var callException = (CallExceptionResponse) result;
      var message = ErrorMessageConvertor.decodeMessage(callException.getMessage());
      throw new RepositoryClientException(message);
    }
    return result;
  }

  /**
   * Создать новый http запрос к серверу хранилищ
   *
   * @param url  адрес сервера хранилищ
   * @param data xml сообщение
   * @return http запрос
   */
  private Request createRequest(String url, String data) {
    var body = RequestBody.create(data, XML_MEDIA_TYPE);
    return new Request.Builder()
      .url(url)
      .post(body)
      .build();
  }

  /**
   * Получить ответ сервера хранилищ в виде строки
   *
   * @param client  http клиент
   * @param request http запрос, созданный с помощью {@link #createRequest}
   * @return ответ сервера хранилищ
   * @throws RepositoryClientException в случае, когда сервер хранилищ вернул пустой ответ или по причине
   *                                   недоступности сервера хранилищ
   */
  private String getResponseBody(OkHttpClient client, Request request) throws RepositoryClientException {
    String responseBody;
    try (Response response = client.newCall(request).execute()) {
      var body = response.body();
      if (body == null) {
        throw new RepositoryClientException("Пустой ответ");
      }
      responseBody = body.string();
    } catch (IOException exception) {
      throw new RepositoryClientException("Не удалось подключиться к хранилищу", exception);
    }
    return responseBody;
  }

}
