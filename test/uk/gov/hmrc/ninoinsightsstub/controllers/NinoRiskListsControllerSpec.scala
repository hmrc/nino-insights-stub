/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ninoinsightsstub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.ninoinsightsstub.config.RejectList
import uk.gov.hmrc.ninoinsightsstub.models.NinoDetails

class NinoRiskListsControllerSpec extends AnyWordSpec with Matchers {

  private val controller = new NinoRiskListsController(Helpers.stubControllerComponents(), new RejectList)

  def requestBuilder(nino: String): FakeRequest[JsValue] =
    FakeRequest("POST", "/reject/nino")
      .withBody(Json.toJson(NinoDetails(nino)))
      .withHeaders(CONTENT_TYPE -> "application/json")

  "POST /reject/nino" should {
    "return 200 with result true when NINO is on reject list" in {

      val result = controller.isNinoOnRejectList()(requestBuilder("ZL806201C"))

      status(result) shouldBe Status.OK
      contentAsJson(result) shouldBe Json.obj("result" -> true)
    }

    "return 200 with result false when NINO is NOT on reject list" in {

      val result = controller.isNinoOnRejectList()(requestBuilder("AB123456A"))

      status(result) shouldBe Status.OK
      contentAsJson(result) shouldBe Json.obj("result" -> false)
    }

    "return 400 when bad JSON is provided" in {

      val fakeRequestWithFalse = FakeRequest("POST", "/reject/nino")
        .withBody(Json.toJson(""))
        .withHeaders(CONTENT_TYPE -> "application/json")

      val result = controller.isNinoOnRejectList()(fakeRequestWithFalse)

      status(result) shouldBe Status.BAD_REQUEST
    }
  }
}
