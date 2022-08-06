/*
 * Copyright 2014 Bang Jun-young
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jeongyookgak.jth.presentation.util.text

/**
 * [KoreanTextMatcher] 매칭 결과를 담고 있는 클래스
 *
 * @author 방준영 &lt;bang.junyoung@gmail.com&gt;
 */
class KoreanTextMatch {
    private val _matcher: KoreanTextMatcher?
    private val _text: String?
    private val _value: String
    private val _index: Int
    private val _success: Boolean

    private constructor() {
        _matcher = null
        _text = null
        _value = ""
        _index = 0
        _success = false
    }

    internal constructor(matcher: KoreanTextMatcher?, text: String, startIndex: Int, length: Int) {
        _matcher = matcher
        _text = text
        _value = text.substring(startIndex, startIndex + length)
        _index = startIndex
        _success = true
    }

    /**
     * 매치가 성공했는지 여부를 조사한다.
     *
     * @return 성공했으면 `true`, 아니면 `false`.
     */
    fun success(): Boolean {
        return _success
    }

    /**
     * 매치의 시작 위치를 구한다.
     *
     * @return 검색 대상 문자열 내 패턴의 시작 위치
     */
    fun index(): Int {
        return _index
    }

    /**
     * 매치의 길이를 구한다.
     *
     * @return 검색 대상 문자열 내 매치의 길이
     */
    fun length(): Int {
        return _value.length
    }

    /**
     * 매치 문자열을 구한다.
     *
     * @return 검색 대상 문자열 내 실제 매치
     */
    fun value(): String {
        return _value
    }

    /**
     * 마지막 매치가 끝나는 위치의 뒷문자부터 시작해서 다음 매치를 찾는다.
     *
     * @return 검색 결과를 담은 [KoreanTextMatch] 인스턴스.
     * [.success]가 `true`일 때만 유효하다.
     * 검색이 실패하면 [.EMPTY]를 리턴한다.
     */
    fun nextMatch(): KoreanTextMatch {
        return if (_text == null) EMPTY else _matcher!!.match(_text, _index + _value.length)
    }

    companion object {
        /**
         * 검색이 실패했을 때 결과로 리턴되는 인스턴스.
         *
         *
         * 이 인스턴스의 [.success]는 항상 `false`다. [.index],
         * [.length], [.value] 등 다른 프로퍼티들의 값은 미정이다.
         */
        val EMPTY = KoreanTextMatch()
    }
}