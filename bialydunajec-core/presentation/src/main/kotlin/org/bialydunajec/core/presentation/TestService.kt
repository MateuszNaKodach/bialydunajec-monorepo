package org.bialydunajec.core.presentation

import org.springframework.stereotype.Service

@Service
class TestService {

    fun helloWorld(): String = TestInternal("Hello World Internal!").text
}

internal class TestInternal(val text: String)