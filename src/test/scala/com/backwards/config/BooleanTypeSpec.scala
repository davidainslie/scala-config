package com.backwards.config

import org.scalacheck.Gen
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class BooleanTypeSpec extends AnyWordSpec with Matchers with ScalaCheckDrivenPropertyChecks {
  val trueTypes: Gen[Any] =
    Gen.oneOf(List(true, "True", "On", 1, "1"))

  val falseTypes: Gen[Any] =
    Gen.oneOf(List(false, "False", "off", 0, "0"))

  "Boolean type" should {
    "conform to true types" in {
      forAll(trueTypes) { t =>
        val Some(b) = BooleanType.unapply(BooleanType(t))

        b mustBe true
      }
    }

    "conform to false types" in {
      forAll(falseTypes) { t =>
        val Some(b) = BooleanType.unapply(BooleanType(t))

        b mustBe false
      }
    }

    "not accept non boolean types" in {
      forAll { s: String =>
        BooleanType.unapply(BooleanType(s)) mustBe None
      }
    }
  }
}