package org.indelible.counter.backgrounds
//
//import androidx.compose.runtime.Composable
//import kotlinx.browser.document
//import kotlinx.html.body
//import kotlinx.html.dom.append
//import kotlinx.html.html
//import kotlinx.html.p
//import kotlinx.html.stream.appendHTML
//import kotlinx.html.video
//
//
//fun videoBackGround(source: String): String{
//    return buildString {
//        appendHTML().html {
//            body {
//                video {
//                    width = "100%"
//                    height = "100%"
//                    autoPlay = true
//                    loop = true
//                    controls = false
//                    src = source
//                }
//            }
//        }
//    }
//}