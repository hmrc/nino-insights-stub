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

import play.api.libs.json._
import play.api.mvc.{Action, BaseController, ControllerComponents}
import uk.gov.hmrc.ninoinsightsstub.config.RejectList
import uk.gov.hmrc.ninoinsightsstub.models.NinoDetails

import javax.inject.Inject

class NinoRiskListsController @Inject() (override val controllerComponents: ControllerComponents, rejectList: RejectList) extends BaseController {

  def isNinoOnRejectList: Action[JsValue] = Action(parse.json) {
    _.body.validate[NinoDetails] match {
      case JsSuccess(model, _) =>
        Ok(Json.obj("result" -> rejectList.ninos.contains(model.nino)))
      case _ =>
        BadRequest(Json.obj("message" -> "malformed request payload"))
    }
  }

}
