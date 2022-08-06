/*
 * Copyright 2019 Bang Jun-young
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

import com.jeongyookgak.jth.presentation.util.text.KoreanChar.isSyllable
import com.jeongyookgak.jth.presentation.util.text.KoreanChar.decomposeCompat
import com.jeongyookgak.jth.presentation.util.text.KoreanChar.isCompatChoseong
import com.jeongyookgak.jth.presentation.util.text.KoreanChar.splitJamo
import com.jeongyookgak.jth.presentation.util.text.KoreanChar.isChoseong
import com.jeongyookgak.jth.presentation.util.text.KoreanChar.choseongToCompatChoseong
import com.jeongyookgak.jth.presentation.util.text.KoreanCharApproxMatcher
import com.jeongyookgak.jth.presentation.util.text.KoreanChar

/**
 * 한글 음절 근사 매칭 클래스.
 *
 *
 * 이 클래스는 문자 수준에서의 음절 근사 매칭 API를 포함하고 있다.
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
object KoreanCharApproxMatcher {
    /**
     * 주어진 두 문자를 음절 근사 매칭으로 비교한다.
     *
     * 비교할 첫번째 문자는 두번째 문자보다 항상 자모 개수가 같거나 많아야 한다.
     * 예를 들어 `isMatch('한', '하')`는 성공하지만 `isMatch('하', '한')`은
     * 실패한다.
     *
     * @param t 비교할 첫번째 문자.
     * @param p 비교할 두번째 문자.
     * @return 매칭이 성공하면 `true`, 실패하면 `false`.
     */
    fun isMatch(t: Char, p: Char): Boolean {
        return decompose(t).startsWith(decompose(p))
    }

    private fun decompose(c: Char): String {
        return if (isSyllable(c)) java.lang.String.join(
            "",
            *decomposeCompat(c)
        ) else if (isCompatChoseong(c)) splitJamo(c) else if (isChoseong(
                c
            )
        ) splitJamo(choseongToCompatChoseong(c)) else c.toString()
    }
}