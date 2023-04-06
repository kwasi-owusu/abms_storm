package com.woleapp.network

import com.woleapp.model.EBiller
import com.woleapp.model.EBillerResponse
import io.reactivex.Single
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import timber.log.Timber


fun getEbillsClientClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(MockInterceptor())
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()


fun getEbillsService(): EbillsApiService = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("http://ebills-test.netpluspay.com:3040/")
    .client(getEbillsClientClient())
    .build().create(EbillsApiService::class.java)


interface EbillsApiService {
    @GET("getAllBillers")
    fun getBillerList(): Single<List<EBiller>>

    @GET("getForm/{billerId}")
    fun getBiller(@Path("billerId") p: Int): Single<String>

    @POST("validateForm")
    fun validateForm(@Body biller: EBillerResponse): Single<String>
}

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        Timber.e("url is")
        Timber.e(uri)
        return chain.proceed(chain.request())
//        val responseString = when {
//            uri.endsWith("getAllBillers") -> billerList
//            uri.endsWith("validateForm") -> validateForm
//            else -> singleBiller
//        }
//        Timber.e("intercepting")
//        //Thread.sleep(2000)
//        return chain.proceed(chain.request())
//            .newBuilder()
//            .code(200)
//            .protocol(Protocol.HTTP_2)
//            .message(responseString)
//            .body(
//                ResponseBody.create(
//                    MediaType.parse("application/json"),
//                    responseString.toByteArray()
//                )
//            )
//            .addHeader("content-type", "application/json")
//            .build()
    }

    val validateForm = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "\t<title>Confirmation</title>\n" +
            "\t<meta charset=\"UTF-8\">\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\t<!-- ===============================================================================================-->\n" +
            "\t<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
            "\t\tintegrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">\n" +
            "\t<!-- ===============================================================================================-->\n" +
            "\t<style>\n" +
            "\t\t@import url(\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600&family=Poppins:wght@300;400;500;600&display=swap\");\n" +
            "\n" +
            "\t\tbody,\n" +
            "\t\thtml {\n" +
            "\t\t\theight: 100%;\n" +
            "\t\t\tfont-family: \"Poppins\", sans-serif;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tp {\n" +
            "\t\t\tfont-family: \"Poppins\", sans-serif;\n" +
            "\t\t\tfont-size: 14px;\n" +
            "\t\t\tline-height: 1.7;\n" +
            "\t\t\tcolor: #666666;\n" +
            "\t\t\tmargin: 0px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput {\n" +
            "\t\t\toutline: none;\n" +
            "\t\t\tborder: none;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus {\n" +
            "\t\t\tborder-color: transparent !important;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus::-webkit-input-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus:-moz-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus::-moz-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus:-ms-input-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput::-webkit-input-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:-moz-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput::-moz-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:-ms-input-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tbutton {\n" +
            "\t\t\toutline: none !important;\n" +
            "\t\t\tborder: none;\n" +
            "\t\t\tbackground: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tbutton:hover {\n" +
            "\t\t\tcursor: pointer;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.form-group {\n" +
            "\t\t\tpadding-bottom: 4%;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form {\n" +
            "\t\t\twidth: 560px;\n" +
            "\t\t\tmin-height: 100vh;\n" +
            "\t\t\tdisplay: block;\n" +
            "\t\t\tbackground-color: #f7f7f7;\n" +
            "\t\t\tpadding: 40px 55px 0px 55px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-title {\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\tdisplay: block;\n" +
            "\t\t\tfont-family: \"Poppins\", sans-serif;\n" +
            "\t\t\tfont-size: 1.2rem;\n" +
            "\t\t\tcolor: #141414;\n" +
            "\t\t\tline-height: 1.2;\n" +
            "\t\t\ttext-align: center;\n" +
            "\t\t\tpadding-bottom: 8%;\n" +
            "\t\t\tmargin-bottom: 20px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.img-rounded {\n" +
            "\t\t\tborder-radius: 50%;\n" +
            "\t\t\twidth: 70px;\n" +
            "\t\t\theight: 70px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.img-logo {\n" +
            "\t\t\tmax-width: 200px;\n" +
            "\t\t\theight: 30px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.img-container {\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t\talign-items: center;\n" +
            "\t\t\tpadding-top: 5px;\n" +
            "\t\t\tpadding-bottom: 5px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.logo-nav {\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t\talign-items: center;\n" +
            "\t\t\tpadding-top: 15px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.logo-nav img {\n" +
            "\t\t\tmargin-top: 2%;\n" +
            "\t\t\theight: 25px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.container-page-form-btn {\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\tdisplay: -webkit-box;\n" +
            "\t\t\tdisplay: -webkit-flex;\n" +
            "\t\t\tdisplay: -moz-box;\n" +
            "\t\t\tdisplay: -ms-flexbox;\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tflex-wrap: wrap;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-btn {\n" +
            "\t\t\tdisplay: -webkit-box;\n" +
            "\t\t\tdisplay: -webkit-flex;\n" +
            "\t\t\tdisplay: -moz-box;\n" +
            "\t\t\tdisplay: -ms-flexbox;\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t\talign-items: center;\n" +
            "\t\t\tpadding: 0 20px;\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\theight: 50px;\n" +
            "\t\t\tborder-radius: 5px;\n" +
            "\t\t\tbackground: #1de255;\n" +
            "\t\t\tfont-family: \"Montserrat\", sans-serif;\n" +
            "\t\t\tfont-size: 17px;\n" +
            "\t\t\tfont-weight: bold;\n" +
            "\t\t\tcolor: #fff;\n" +
            "\t\t\tline-height: 1.2;\n" +
            "\t\t\ttext-transform: uppercase;\n" +
            "\t\t\tletter-spacing: 1px;\n" +
            "\t\t\t-webkit-transition: all 0.4s;\n" +
            "\t\t\t-o-transition: all 0.4s;\n" +
            "\t\t\t-moz-transition: all 0.4s;\n" +
            "\t\t\ttransition: all 0.4s;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-btn:hover {\n" +
            "\t\t\tbackground: #198a39;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-span-one {\n" +
            "\t\t\tfont-weight: bolder;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-span-two {\n" +
            "\t\t\tfont-weight: lighter;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-btn:disabled {\n" +
            "\t\t\tbackground: #6d8d76;\n" +
            "\t\t\tcursor: default;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t@media (max-width: 992px) {\n" +
            "\t\t\t.payment-form {\n" +
            "\t\t\t\twidth: 50%;\n" +
            "\t\t\t\tpadding-left: 30px;\n" +
            "\t\t\t\tpadding-right: 30px;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.main-img {\n" +
            "\t\t\t\twidth: 50%;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t@media (max-width: 768px) {\n" +
            "\t\t\t.payment-form {\n" +
            "\t\t\t\twidth: 100%;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.main-img {\n" +
            "\t\t\t\tdisplay: none;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t@media (max-width: 576px) {\n" +
            "\t\t\t.payment-form {\n" +
            "\t\t\t\tpadding-left: 15px;\n" +
            "\t\t\t\tpadding-right: 15px;\n" +
            "\t\t\t\tpadding-top: 70px;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.hidden {\n" +
            "\t\t\tdisplay: none;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tsmall {\n" +
            "\t\t\tcolor: #ff0000;\n" +
            "\t\t}\n" +
            "\t</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\t<div class=\"container\">\n" +
            "\t\t<form class=\"payment-form\" id=\"data-form\"\n" +
            "\t\t\tstyle=\"padding-top: 25px !important; padding-bottom: 25px; margin:auto;\">\n" +
            "\t\t\t<span class=\"img-container mb-5\"><img class=\"img-logo\" src=\"data:image/png;base64,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\" alt=\"logo\"></span>\n" +
            "\t\t\t<h2 class=\"text-center\">Confirmation</h2>\n" +
            "\t\t\t<div class=\"form-group hidden\"><input class=\"form-control\" type=\"text\" name=\"step\" value=\"2\"></div>\n" +
            "\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"user\" value=\"John Carpenter\"></div>\n" +
            "\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"formId\" value=\"3\"></div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group hidden\"><input class=\"form-control\" type=\"text\" name=\"url\"></div>\n" +
            "\t\t\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"productId\" value=\"2\"></div>\n" +
            "\t\t\t\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"billerId\" value=\"15\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" name=\"BillofLadingNumber\" type=\"text\" placeholder=\"Bill of Lading no\" value=\"36484jfkdnru\" required=\"true\" readonly=\"\" maxlength=\"30\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" name=\"InvoiceDate\" type=\"date\" placeholder=\"Invoice Date\" value=\"2021-03-31\" required=\"true\" readonly=\"\" maxlength=\"\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" name=\"Amount\" type=\"text\" placeholder=\"Amount\" value=\"undefined\" required=\"true\" readonly=\"\" maxlength=\"\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"container-page-form-btn\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<button class=\"payment-form-btn btn btn-success\" id=\"pay\" type=\"submit\">Pay</button>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"loader-div\"></div>\n" +
            "\t\t</form>\n" +
            "\t</div>\n" +
            "\t<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"\n" +
            "\t\tintegrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\">\n" +
            "\t</script>\n" +
            "\t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"\n" +
            "\t\tintegrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\">\n" +
            "\t</script>\n" +
            "\t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.6/clipboard.min.js\"></script>\n" +
            "\t<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js\"\n" +
            "\t\tintegrity=\"sha384-LtrjvnR4Twt/qOuYxE721u19sVFLVSA4hf/rRt6PrZTmiPltdZcI7q7PXQBYTKyf\" crossorigin=\"anonymous\">\n" +
            "\t</script>\n" +
            "\t<script>\n" +
            "\t\tlet dataForm = document.getElementById(\"data-form\");\n" +
            "let container = document.getElementById(\"loader-div\");\n" +
            "let button = document.getElementById(\"pay\");\n" +
            "\n" +
            "const showLoader = () => {\n" +
            "  let loader = `<div class=\"d-flex justify-content-center\">\n" +
            "  <div class=\"spinner-border text-success\" style=\"width: 4rem; height: 4rem;\" role=\"status\">\n" +
            "    <span class=\"sr-only\">Loading...</span>\n" +
            "  </div>\n" +
            "  </div>`;\n" +
            "  button.classList.add(\"hidden\");\n" +
            "  container.innerHTML = \"\";\n" +
            "  container.insertAdjacentHTML(\"beforeend\", loader);\n" +
            "  };\n" +
            "\n" +
            "  const hideLoader = () => {\n" +
            "  container.innerHTML = \"\";\n" +
            "  button.classList.remove(\"hidden\");\n" +
            "  };\n" +
            "\n" +
            "  const getFormData = (formId) => {\n" +
            "    const formValues = [];\n" +
            "    const formNames = [];\n" +
            "    const kvPair = [];\n" +
            "    const form = document.getElementById(formId);\n" +
            "    for (let i = 0; i < form.elements.length; i++) {\n" +
            "      const e = form.elements[i];\n" +
            "      formValues.push(e.value);\n" +
            "      formNames.push(e.name);\n" +
            "    }\n" +
            "\n" +
            "    for (let u = 0; u < formNames.length; u++) {\n" +
            "      kvPair.push(`\"\${formNames[u]}\": \"\${formValues[u]}\"`);\n" +
            "    }\n" +
            "\n" +
            "    const final = JSON.parse(`{\${kvPair}}`);\n" +
            "    return final;\n" +
            "  };\n" +
            "\n" +
            "  dataForm.addEventListener(\"submit\", (event) => {\n" +
            "    event.preventDefault();\n" +
            "\n" +
            "    let { billerId, productId, formId, step, user, url, ...fields } = getFormData(\n" +
            "      \"data-form\"\n" +
            "    );\n" +
            "\n" +
            "    let data = JSON.stringify({\n" +
            "      billerId,\n" +
            "      productId,\n" +
            "      formId,\n" +
            "      step,\n" +
            "      user,\n" +
            "      fields,\n" +
            "    });\n" +
            "\n" +
            "    showLoader();\n" +
            "    Android.sendData(data);\n" +
            "    });\n" +
            "\t</script>\n" +
            "</body>\n" +
            "\n" +
            "</html>"

    val billerList = """
        [
    {
        "biller": "Nigerian Breweries",
        "billerId": "10",
        "billerImage": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHA4DCG1YG3_dSQm12bqSIomDm3-th7KUc1Q3Nbl3rwCtAZpmkK1GnsAsQu3_3oDdaDTU&usqp=CAU"
    },
    {
        "biller": "SevenUp Bottling Company",
        "billerId": "25",
        "billerImage": "https://i0.wp.com/www.naijabusiness.com.ng/wp-content/uploads/2020/08/Seven-Up-Bottling-Company-Ltd.jpg"
    },
    {
        "biller": "Enyo",
        "billerId": "15",
        "billerImage": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuyGphU5nvqdXSmcH62c1ZD0YqrJfovDu1H7snJ2iY6FfqXvzuJyDEBEnImsJjoBNN3To&usqp=CAU"
    },
    {
        "biller": "Promasidor",
        "billerId": "30",
        "billerImage": "https://149520306.v2.pressablecdn.com/wp-content/uploads/2016/09/promasidor_logo-750x400.jpg"
    },
    {
        "biller": "Nigerian Bottling Company Plc",
        "billerId": "17",
        "billerImage": "https://i0.wp.com/www.brandcrunch.com.ng/wp-content/uploads/2017/11/Nigerian-Bottling-Company.jpg?resize=640%2C400&ssl=1"
    },
    {
        "biller": "Total",
        "billerId": "07",
        "billerImage": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_V7VR2aEOuI33YlwH-RwqBp6TT1wuf5qLnxmXNWKik4yska-aW-uXx7UVnOmzafVVBs8&usqp=CAU"
    },
    {
        "biller": "CHI",
        "billerId": "29",
        "billerImage": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfBTbLxpUBI5Qd9q2TrFb1U6Im0PmO3QyJVjaqCuXgxljRkwy6KWX8OsglUUPrmhE3xSg&usqp=CAU"
    }
]
"""
    val singleBiller = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "\t<title></title>\n" +
            "\t<meta charset=\"UTF-8\">\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\t<!-- ===============================================================================================-->\n" +
            "\t<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
            "\t\tintegrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">\n" +
            "\t<!-- ===============================================================================================-->\n" +
            "\t<style>\n" +
            "\t\t@import url(\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600&family=Poppins:wght@300;400;500;600&display=swap\");\n" +
            "\n" +
            "\t\tbody,\n" +
            "\t\thtml {\n" +
            "\t\t\theight: 100%;\n" +
            "\t\t\tfont-family: \"Poppins\", sans-serif;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tp {\n" +
            "\t\t\tfont-family: \"Poppins\", sans-serif;\n" +
            "\t\t\tfont-size: 14px;\n" +
            "\t\t\tline-height: 1.7;\n" +
            "\t\t\tcolor: #666666;\n" +
            "\t\t\tmargin: 0px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput {\n" +
            "\t\t\toutline: none;\n" +
            "\t\t\tborder: none;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus {\n" +
            "\t\t\tborder-color: transparent !important;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus::-webkit-input-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus:-moz-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus::-moz-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:focus:-ms-input-placeholder {\n" +
            "\t\t\tcolor: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput::-webkit-input-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:-moz-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput::-moz-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tinput:-ms-input-placeholder {\n" +
            "\t\t\tcolor: #999999;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tbutton {\n" +
            "\t\t\toutline: none !important;\n" +
            "\t\t\tborder: none;\n" +
            "\t\t\tbackground: transparent;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tbutton:hover {\n" +
            "\t\t\tcursor: pointer;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.form-group {\n" +
            "\t\t\tpadding-bottom: 4%;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form {\n" +
            "\t\t\twidth: 560px;\n" +
            "\t\t\tmin-height: 100vh;\n" +
            "\t\t\tdisplay: block;\n" +
            "\t\t\tbackground-color: #f7f7f7;\n" +
            "\t\t\tpadding: 40px 55px 0px 55px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-title {\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\tdisplay: block;\n" +
            "\t\t\tfont-family: \"Poppins\", sans-serif;\n" +
            "\t\t\tfont-size: 1.2rem;\n" +
            "\t\t\tcolor: #141414;\n" +
            "\t\t\tline-height: 1.2;\n" +
            "\t\t\ttext-align: center;\n" +
            "\t\t\tpadding-bottom: 8%;\n" +
            "\t\t\tmargin-bottom: 20px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.img-rounded {\n" +
            "\t\t\tborder-radius: 50%;\n" +
            "\t\t\twidth: 70px;\n" +
            "\t\t\theight: 70px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.img-logo {\n" +
            "\t\t\tmax-width: 100px;\n" +
            "\t\t\theight: 30px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.img-container {\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t\talign-items: center;\n" +
            "\t\t\tpadding-top: 5px;\n" +
            "\t\t\tpadding-bottom: 5px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.logo-nav {\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t\talign-items: center;\n" +
            "\t\t\tpadding-top: 15px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.logo-nav img {\n" +
            "\t\t\tmargin-top: 2%;\n" +
            "\t\t\theight: 25px;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.container-page-form-btn {\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\tdisplay: -webkit-box;\n" +
            "\t\t\tdisplay: -webkit-flex;\n" +
            "\t\t\tdisplay: -moz-box;\n" +
            "\t\t\tdisplay: -ms-flexbox;\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tflex-wrap: wrap;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-btn {\n" +
            "\t\t\tdisplay: -webkit-box;\n" +
            "\t\t\tdisplay: -webkit-flex;\n" +
            "\t\t\tdisplay: -moz-box;\n" +
            "\t\t\tdisplay: -ms-flexbox;\n" +
            "\t\t\tdisplay: flex;\n" +
            "\t\t\tjustify-content: center;\n" +
            "\t\t\talign-items: center;\n" +
            "\t\t\tpadding: 0 20px;\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\theight: 50px;\n" +
            "\t\t\tborder-radius: 5px;\n" +
            "\t\t\tbackground: #1de255;\n" +
            "\t\t\tfont-family: \"Montserrat\", sans-serif;\n" +
            "\t\t\tfont-size: 17px;\n" +
            "\t\t\tfont-weight: bold;\n" +
            "\t\t\tcolor: #fff;\n" +
            "\t\t\tline-height: 1.2;\n" +
            "\t\t\ttext-transform: uppercase;\n" +
            "\t\t\tletter-spacing: 1px;\n" +
            "\t\t\t-webkit-transition: all 0.4s;\n" +
            "\t\t\t-o-transition: all 0.4s;\n" +
            "\t\t\t-moz-transition: all 0.4s;\n" +
            "\t\t\ttransition: all 0.4s;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-btn:hover {\n" +
            "\t\t\tbackground: #198a39;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-span-one {\n" +
            "\t\t\tfont-weight: bolder;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-span-two {\n" +
            "\t\t\tfont-weight: lighter;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.payment-form-btn:disabled {\n" +
            "\t\t\tbackground: #6d8d76;\n" +
            "\t\t\tcursor: default;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t@media (max-width: 992px) {\n" +
            "\t\t\t.payment-form {\n" +
            "\t\t\t\twidth: 50%;\n" +
            "\t\t\t\tpadding-left: 30px;\n" +
            "\t\t\t\tpadding-right: 30px;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.main-img {\n" +
            "\t\t\t\twidth: 50%;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t@media (max-width: 768px) {\n" +
            "\t\t\t.payment-form {\n" +
            "\t\t\t\twidth: 100%;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t\t.main-img {\n" +
            "\t\t\t\tdisplay: none;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t@media (max-width: 576px) {\n" +
            "\t\t\t.payment-form {\n" +
            "\t\t\t\tpadding-left: 15px;\n" +
            "\t\t\t\tpadding-right: 15px;\n" +
            "\t\t\t\tpadding-top: 70px;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t.hidden {\n" +
            "\t\t\tdisplay: none;\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tsmall {\n" +
            "\t\t\tcolor: #ff0000;\n" +
            "\t\t}\n" +
            "\t</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\t<div class=\"container\">\n" +
            "\t\t<form class=\"payment-form\" id=\"data-form\"\n" +
            "\t\t\tstyle=\"padding-top: 25px !important; padding-bottom: 25px; margin:auto;\">\n" +
            "\t\t\t<span class=\"img-container mb-5\"><img class=\"img-logo\" src=\"data:image/png;base64,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\" alt=\"logo\"></span>\n" +
            "\t\t\t<div class=\"form-group hidden\"><input class=\"form-control\" type=\"text\" name=\"step\" value=\"1\"></div>\n" +
            "\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"user\" value=\"John Carpenter\"></div>\n" +
            "\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"formId\" value=\"2\"></div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"url\" value=\"http://localhost:3040/validateProductForm\"></div>\n" +
            "\t\t\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"productId\" value=\"2\"></div>\n" +
            "\t\t\t\t\t\t\t\t<div class=\"form-group hidden\">\n" +
            "\t\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" name=\"billerId\" value=\"15\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" name=\"BillofLadingNumber\" type=\"text\" placeholder=\"Bill of Lading no\" value=\"\" required=\"true\" maxlength=\"30\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" name=\"InvoiceDate\" type=\"date\" placeholder=\"Invoice Date\" value=\"\" required=\"true\" maxlength=\"\"></div>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<div class=\"container-page-form-btn\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<button class=\"payment-form-btn btn btn-success\" id=\"pay\" type=\"submit\">Proceed</button>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<div id=\"loader-div\"></div>\n" +
            "\t\t</form>\n" +
            "\t</div>\n" +
            "\t<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"\n" +
            "\t\tintegrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\">\n" +
            "\t</script>\n" +
            "\t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"\n" +
            "\t\tintegrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\">\n" +
            "\t</script>\n" +
            "\t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.6/clipboard.min.js\"></script>\n" +
            "\t<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js\"\n" +
            "\t\tintegrity=\"sha384-LtrjvnR4Twt/qOuYxE721u19sVFLVSA4hf/rRt6PrZTmiPltdZcI7q7PXQBYTKyf\" crossorigin=\"anonymous\">\n" +
            "\t</script>\n" +
            "\t<script>\n" +
            "\t\tlet dataForm = document.getElementById(\"data-form\");\n" +
            "let container = document.getElementById(\"loader-div\");\n" +
            "let button = document.getElementById(\"pay\");\n" +
            "\n" +
            "const showLoader = () => {\n" +
            "  let loader = `<div class=\"d-flex justify-content-center\">\n" +
            "  <div class=\"spinner-border text-success\" style=\"width: 4rem; height: 4rem;\" role=\"status\">\n" +
            "    <span class=\"sr-only\">Loading...</span>\n" +
            "  </div>\n" +
            "  </div>`;\n" +
            "  button.classList.add(\"hidden\");\n" +
            "  container.innerHTML = \"\";\n" +
            "  container.insertAdjacentHTML(\"beforeend\", loader);\n" +
            "  };\n" +
            "\n" +
            "  const hideLoader = () => {\n" +
            "  container.innerHTML = \"\";\n" +
            "  button.classList.remove(\"hidden\");\n" +
            "  };\n" +
            "\n" +
            "  const getFormData = (formId) => {\n" +
            "    const formValues = [];\n" +
            "    const formNames = [];\n" +
            "    const kvPair = [];\n" +
            "    const form = document.getElementById(formId);\n" +
            "    for (let i = 0; i < form.elements.length; i++) {\n" +
            "      const e = form.elements[i];\n" +
            "      formValues.push(e.value);\n" +
            "      formNames.push(e.name);\n" +
            "    }\n" +
            "\n" +
            "    for (let u = 0; u < formNames.length; u++) {\n" +
            "      kvPair.push(`\"\${formNames[u]}\": \"\${formValues[u]}\"`);\n" +
            "    }\n" +
            "\n" +
            "    const final = JSON.parse(`{\${kvPair}}`);\n" +
            "    return final;\n" +
            "  };\n" +
            "\n" +
            "  dataForm.addEventListener(\"submit\", (event) => {\n" +
            "    event.preventDefault();\n" +
            "\n" +
            "    let { billerId, productId, formId, step, user, url, ...fields } = getFormData(\n" +
            "      \"data-form\"\n" +
            "    );\n" +
            "\n" +
            "    let data = JSON.stringify({\n" +
            "      billerId,\n" +
            "      productId,\n" +
            "      formId,\n" +
            "      step,\n" +
            "      user,\n" +
            "      fields,\n" +
            "    });\n" +
            "\n" +
            "    showLoader();\n" +
            "    Android.sendData(data);\n" +
            "    //- try {\n" +
            "    //-   fetch(url, {\n" +
            "    //-   method: \"POST\",\n" +
            "    //-   body: data,\n" +
            "    //-   }).then(async (response) => {\n" +
            "    //-   if (response.ok) {\n" +
            "    //-     let body = await response.json();\n" +
            "    //-     Android.sendData(JSON.stringify(body));\n" +
            "    //-   } else {\n" +
            "    //-     alert(\"form submission failed.\\nPlease try again\");\n" +
            "    //-     hideLoader();\n" +
            "    //-   }\n" +
            "    //-   });\n" +
            "    //- } catch (err) {\n" +
            "    //-   console.log(err);\n" +
            "    //-   alert(\"form submission failed.\\nPlease Try again\");\n" +
            "    //-   hideLoader();\n" +
            "    //- }\n" +
            "    });\n" +
            "\t</script>\n" +
            "</body>\n" +
            "\n" +
            "</html>"
}
