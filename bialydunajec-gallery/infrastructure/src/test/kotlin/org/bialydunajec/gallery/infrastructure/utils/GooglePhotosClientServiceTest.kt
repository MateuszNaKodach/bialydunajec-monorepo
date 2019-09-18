package org.bialydunajec.gallery.infrastructure.utils

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.api.gax.grpc.GrpcStatusCode
import com.google.api.gax.rpc.ApiException
import com.google.photos.library.v1.upload.UploadMediaItemResponse
import io.grpc.Status
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature


object GooglePhotosClientServiceTest: Spek ({

    Feature("Getting upload media token") {
        lateinit var uploadResponse: UploadMediaItemResponse

        Scenario("Get token when no error") {
            lateinit var token: String
            Given("Set response with token") {
                token = "token"
                uploadResponse = UploadMediaItemResponse.newBuilder()
                    .setUploadToken(token)
                    .build()
            }

            lateinit var result: String
            When("Getting token") {
                result = GooglePhotosClientService.getUploadMediaItemTokenIfNoError(uploadResponse)
            }

            Then("Token should be obtained") {
                assertThat(result).isEqualTo(token)
            }
        }

        Scenario("No token when error") {
            Given("Set response with error") {
                val throwable = Throwable("message")
                val statusCode = GrpcStatusCode.of(Status.Code.UNAUTHENTICATED)
                val apiException = ApiException(throwable, statusCode, false)
                val error = UploadMediaItemResponse.Error.newBuilder()
                        .setCause(apiException)
                        .build()
                uploadResponse = UploadMediaItemResponse.newBuilder()
                        .setError(error)
                        .build()
            }

            lateinit var result: String
            When("Getting token") {
                result = GooglePhotosClientService.getUploadMediaItemTokenIfNoError(uploadResponse)
            }

            Then("Token is empty") {
                assertThat(result).isEqualTo(String())
            }
        }
    }
})
