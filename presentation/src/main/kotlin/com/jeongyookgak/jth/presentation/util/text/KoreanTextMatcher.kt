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

import com.jeongyookgak.jth.presentation.util.text.KoreanTextMatch
import com.jeongyookgak.jth.presentation.util.text.KoreanCharApproxMatcher
import com.jeongyookgak.jth.presentation.util.text.KoreanTextMatcher
import java.lang.UnsupportedOperationException

/**
 * 한글 음절 근사 매칭 클래스.
 *
 *
 * 이 클래스는 문자 수준에서의 음절 근사 매칭 API를 포함하고 있는
 * [KoreanCharApproxMatcher]의 상위 서비스로 문자열 수준에서의 음절 근사 매칭
 * API를 포함하고 있다.
 *
 *
 * 한글 음절 근사 매칭은 두 개의 한글 음절을 비교할 때 음절을 이루는 자모의 일부만
 * 일치해도 두 음절이 같은 것으로 간주하는 매칭 방법이다. 예를 들어 일반적인 문자
 * 비교에서 `'밝'`은 `'ㅂ'`, `'바'`, `'발'`과 다른 문자로
 * 간주되지만 음절 근사 매칭에서는 `'밝'`에 `'ㅂ'`, `'바'`,
 * `'발'`이 모두 부합하는 것으로 간주한다.
 *
 *
 * 단, 여기서 주의할 점은 자모의 개수가 다른 두 음절간 비교시 교환성이 성립하지
 * 않는다는 것이다. 예를 들어 `'발'`은 `'밝'`에 부합하지만
 * `'밝'`은 `'발'`에 부합하지 않는다. 즉, 자모의 개수가 더 많은 음절에
 * 대해 자모의 개수가 더 적은 음절을 비교하는 쪽으로 매칭이 이루어져야 한다는 뜻이다.
 *
 *
 * 매칭에 사용할 자모로 Unicode Hangul Jamo와 Hangul Compatibility Jamo를 모두
 * 지원한다.
 *
 * @author 방준영 &lt;bang.junyoung@gmail.com&gt;
 */
class KoreanTextMatcher(pattern: String?) {
    private var _pattern: String? = null
    private var _foundStartAnchor = false
    private var _foundEndAnchor = false
    /**
     * 주어진 `text`에 대해 [.KoreanTextMatcher]에서 지정해 둔
     * `pattern`의 첫번째 출현을 찾는다.
     *
     * @param text 검색 대상 문자열
     * @param startIndex 검색을 시작할 `text` 내 위치
     * @return 검색 결과를 담은 [KoreanTextMatch] 인스턴스.
     * [KoreanTextMatch.success]가 `true`일 때만 유효하다.
     * 검색이 실패하면 [KoreanTextMatch.EMPTY]를 리턴한다.
     * @throws IllegalArgumentException `text`가 `null`일 때,
     * 또는 `startIndex`가 `0`보다 작거나
     * [text.length()][KoreanTextMatch.length]보다 클 때.
     */
    /**
     * 주어진 `text`에 대해 [.KoreanTextMatcher]에서 지정해 둔
     * `pattern`의 첫번째 출현을 찾는다.
     *
     * `text` 내 검색 시작 위치를 지정하려면 [.match]를 사용한다.
     *
     * @param text 검색 대상 문자열
     * @return 검색 결과를 담은 [KoreanTextMatch] 인스턴스.
     * [KoreanTextMatch.success]가 `true`일 때만 유효하다.
     * 검색이 실패하면 [KoreanTextMatch.EMPTY]를 리턴한다.
     * @throws IllegalArgumentException `text`가 `null`일 때.
     */
    @JvmOverloads
    fun match(text: String?, startIndex: Int = 0): KoreanTextMatch {
        requireNotNull(text) { "text: null" }
        require(startIndex >= 0) { "startIndex: $startIndex < 0" }
        require(startIndex <= text.length) {
            String.format(
                "startIndex: %d > text.length(): %d",
                startIndex,
                text.length
            )
        }

        //
        // Optimization: narrow the range of text to be matched for pattern.
        //
        val textRange = getTextRange(text, _pattern!!.length, startIndex)
        if (textRange == -1L) return KoreanTextMatch.EMPTY
        // textRange is a tuple of (int startIndex, int length).
        val startIndexOpt = (textRange shr 32).toInt()
        val length = (textRange and 0xFFFFFFF).toInt()
        return if (length == 0) KoreanTextMatch(this, text, 0, 0) else match(
            text,
            startIndexOpt,
            length
        )
    }
    /**
     * 주어진 `text`에 대해 [.KoreanTextMatcher]에서 지정해 둔
     * `pattern`의 모든 출현을 찾는다.
     *
     * @param text 검색 대상 문자열
     * @param startIndex 검색을 시작할 `text` 내 위치
     * @return 검색 결과를 담은 `Iterable<KoreanTextMatch>` 인스턴스.
     * @throws IllegalArgumentException `text`가 `null`일 때,
     * 또는 `startIndex`가 `0`보다 작거나
     * [text.length()][KoreanTextMatch.length]보다 클 때.
     */
    /**
     * 주어진 `text`에 대해 [.KoreanTextMatcher]에서 지정해 둔
     * `pattern`의 모든 출현을 찾는다.
     *
     * `text` 내 검색 시작 위치를 지정하려면 [.matches]를
     * 사용한다.
     *
     * @param text 검색 대상 문자열
     * @return 검색 결과를 담은 `Iterable<KoreanTextMatch>` 인스턴스.
     * 찾은 것이 없으면 빈 리스트를 리턴한다.
     * @throws IllegalArgumentException `text`가 `null`일 때.
     */
    @JvmOverloads
    fun matches(text: String?, startIndex: Int = 0): Iterable<KoreanTextMatch> {
        return object : Iterable<KoreanTextMatch> {
            override fun iterator(): Iterator<KoreanTextMatch> {
                return object : MutableIterator<KoreanTextMatch> {
                    var _match = match(text, startIndex)
                    override fun hasNext(): Boolean {
                        return _match.success()
                    }

                    override fun next(): KoreanTextMatch {
                        val result = _match
                        _match = _match.nextMatch()
                        return result
                    }

                    override fun remove() {
                        throw UnsupportedOperationException()
                    }
                }
            }
        }
    }

    private fun match(text: String, startIndex: Int, length: Int): KoreanTextMatch {
        if (_pattern!!.length == 0) return KoreanTextMatch(this, text, 0, 0)
        for (i in startIndex until startIndex + length - _pattern!!.length + 1) {
            for (j in 0 until _pattern!!.length) {
                if (!KoreanCharApproxMatcher.isMatch(text[i + j], _pattern!![j])) break
                if (j == _pattern!!.length - 1) return KoreanTextMatch(
                    this,
                    text,
                    i,
                    _pattern!!.length
                )
            }
        }
        return KoreanTextMatch.EMPTY
    }

    private fun stripAnchors(pattern: String): String {
        if (!_foundStartAnchor && !_foundEndAnchor) return pattern
        val startIndex = if (_foundStartAnchor) 1 else 0
        val length =
            pattern.length - (if (_foundStartAnchor) 1 else 0) - if (_foundEndAnchor) 1 else 0
        return pattern.substring(startIndex, startIndex + length)
    }

    private fun getTextRange(text: String, hintLength: Int, startIndex: Int): Long {
        var startIndex = startIndex
        val trimStart = _foundEndAnchor
        val trimEnd = _foundStartAnchor
        var length = text.length - startIndex
        if (length < hintLength) return -1
        if (trimStart && trimEnd) {
            if (text.length != hintLength) return -1
        } else if (trimStart) {
            startIndex = text.length - hintLength
            length = hintLength
        } else if (trimEnd) {
            if (startIndex != 0) return -1
            length = hintLength
        }
        return startIndex.toLong() shl 32 or length.toLong()
    }

    companion object {
        /**
         * 주어진 `text` 내에 주어진 `pattern`이 존재하는지 여부를 조사한다.
         *
         * 정규식 앵커 `^`와 `$`를 사용하여 `pattern`의 위치를
         * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
         *
         * @param text 검색 대상 문자열
         * @param pattern 검색할 패턴
         * @return `text` 내에 `pattern`이 존재하면 `true`,
         * 그렇지 않으면 `false`.
         * @throws IllegalArgumentException `text` 또는 `pattern`이
         * `null`일 때.
         */
        fun isMatch(text: String?, pattern: String?): Boolean {
            return match(text, pattern).success()
        }

        /**
         * 주어진 `text` 내에서 주어진 `pattern`의 첫번째 출현을 찾는다.
         *
         * 모든 출현을 찾으려면 [.matches]를 사용한다.
         *
         * 정규식 앵커 `^`와 `$`를 사용하여 `pattern`의 위치를
         * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
         *
         * @param text 검색 대상 문자열
         * @param pattern 검색할 패턴
         * @return 검색 결과를 담은 [KoreanTextMatch] 인스턴스.
         * [KoreanTextMatch.success]가 `true`일 때만 유효하다.
         * 검색이 실패하면 [KoreanTextMatch.EMPTY]를 리턴한다.
         * @throws IllegalArgumentException `text` 또는 `pattern`이
         * `null`일 때.
         */
        fun match(text: String?, pattern: String?): KoreanTextMatch {
            return KoreanTextMatcher(pattern).match(text)
        }

        /**
         * 주어진 `text` 내에서 주어진 `pattern`의 모든 출현을 찾는다.
         *
         * 첫번째 출현만 찾으려면 [.match]를 사용한다.
         *
         * 정규식 앵커 `^`와 `$`를 사용하여 `pattern`의 위치를
         * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
         *
         * @param text 검색 대상 문자열
         * @param pattern 검색할 패턴
         * @return 검색 결과를 담은 `Iterable<KoreanTextMatch>` 인스턴스.
         * @throws IllegalArgumentException `text` 또는 `pattern`이
         * `null`일 때.
         */
        fun matches(text: String?, pattern: String?): Iterable<KoreanTextMatch> {
            return KoreanTextMatcher(pattern).matches(text)
        }
    }

    /**
     * [KoreanTextMatcher] 클래스의 새 인스턴스를 초기화한다.
     *
     * 정규식 앵커 `^`와 `$`를 사용하여 `pattern`의 위치를
     * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
     *
     * @param pattern 검색할 패턴
     * @throws IllegalArgumentException `pattern`이 `null`일 때.
     */
    init {
        requireNotNull(pattern) { "pattern: null" }
        if (pattern.length == 0) {
            _foundEndAnchor = false
            _foundStartAnchor = _foundEndAnchor
            _pattern = pattern
        } else {
            _foundStartAnchor = pattern[0] == '^'
            _foundEndAnchor = pattern[pattern.length - 1] == '$'
            _pattern = stripAnchors(pattern)
        }
    }
}