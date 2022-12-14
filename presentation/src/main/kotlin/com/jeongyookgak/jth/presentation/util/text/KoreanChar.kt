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

import com.jeongyookgak.jth.presentation.util.text.KoreanChar
import java.util.*

/**
 * 한글 처리에 필요한 다양한 종류의 도우미 함수들을 담고 있는 클래스
 *
 * Unicode Hangul Jamo와 Unicode Hangul Compatibility Jamo를 모두 지원한다.
 *
 * 이 클래스는 인스턴스 생성이 불가능하다.
 *
 * @author 방준영 &lt;bang.junyoung@gmail.com&gt;
 */
object KoreanChar {
    private const val CHOSEONG_COUNT = 19
    private const val JUNGSEONG_COUNT = 21
    private const val JONGSEONG_COUNT = 28
    private const val HANGUL_SYLLABLE_COUNT = CHOSEONG_COUNT * JUNGSEONG_COUNT * JONGSEONG_COUNT
    private const val HANGUL_SYLLABLES_BASE = 0xAC00
    private const val HANGUL_SYLLABLES_END = HANGUL_SYLLABLES_BASE + HANGUL_SYLLABLE_COUNT
    private val COMPAT_CHOSEONG_COLLECTION = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
        'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    private val COMPAT_JUNGSEONG_COLLECTION = charArrayOf(
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
        'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
        'ㅣ'
    )
    private val COMPAT_JONGSEONG_COLLECTION = charArrayOf(
        '\u0000',
        'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ',
        'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ',
        'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    private val JAMO_STRINGS = arrayOf(
        "",
        "ᄀ", "ᄀᄀ", "ᄂ", "ᄃ", "ᄃᄃ", "ᄅ", "ᄆ", "ᄇ", "ᄇᄇ", "ᄉ",
        "ᄉᄉ", "ᄋ", "ᄌ", "ᄌᄌ", "ᄎ", "ᄏ", "ᄐ", "ᄑ", "ᄒ",
        "ᅡ", "ᅢ", "ᅣ", "ᅤ", "ᅥ", "ᅦ", "ᅧ", "ᅨ", "ᅩ", "ᅩᅡ",
        "ᅩᅢ", "ᅩᅵ", "ᅭ", "ᅮ", "ᅮᅥ", "ᅮᅦ", "ᅮᅵ", "ᅲ", "ᅳ", "ᅳᅵ",
        "ᅵ",
        "ᆨ", "ᆨᆨ", "ᆨᆺ", "ᆫ", "ᆫᆽ", "ᆫᇂ", "ᆮ", "ᆯ", "ᆯᆨ", "ᆯᆷ",
        "ᆯᆸ", "ᆯᆺ", "ᆯᇀ", "ᆯᇁ", "ᆯᇂ", "ᆷ", "ᆸ", "ᆸᆺ", "ᆺ", "ᆺᆺ",
        "ᆼ", "ᆽ", "ᆾ", "ᆿ", "ᇀ", "ᇁ", "ᇂ",
        "ㄱ", "ㄱㄱ", "ㄱㅅ", "ㄴ", "ㄴㅈ", "ㄴㅎ", "ㄷ", "ㄷㄷ", "ㄹ", "ㄹㄱ",
        "ㄹㅁ", "ㄹㅂ", "ㄹㅅ", "ㄹㅌ", "ㄹㅍ", "ㄹㅎ", "ㅁ", "ㅂ", "ㅂㅂ", "ㅂㅅ",
        "ㅅ", "ㅅㅅ", "ㅇ", "ㅈ", "ㅈㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ",
        "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅗㅏ",
        "ㅗㅐ", "ㅗㅣ", "ㅛ", "ㅜ", "ㅜㅓ", "ㅜㅔ", "ㅜㅣ", "ㅠ", "ㅡ", "ㅡㅣ",
        "ㅣ"
    )
    private val JAMO_CHARS = charArrayOf(
        '\u0000',
        'ᄀ', 'ᄁ', 'ᄂ', 'ᄃ', 'ᄄ', 'ᄅ', 'ᄆ', 'ᄇ', 'ᄈ', 'ᄉ',
        'ᄊ', 'ᄋ', 'ᄌ', 'ᄍ', 'ᄎ', 'ᄏ', 'ᄐ', 'ᄑ', 'ᄒ',
        'ᅡ', 'ᅢ', 'ᅣ', 'ᅤ', 'ᅥ', 'ᅦ', 'ᅧ', 'ᅨ', 'ᅩ', 'ᅪ',
        'ᅫ', 'ᅬ', 'ᅭ', 'ᅮ', 'ᅯ', 'ᅰ', 'ᅱ', 'ᅲ', 'ᅳ', 'ᅴ',
        'ᅵ',
        'ᆨ', 'ᆩ', 'ᆪ', 'ᆫ', 'ᆬ', 'ᆭ', 'ᆮ', 'ᆯ', 'ᆰ', 'ᆱ',
        'ᆲ', 'ᆳ', 'ᆴ', 'ᆵ', 'ᆶ', 'ᆷ', 'ᆸ', 'ᆹ', 'ᆺ', 'ᆻ',
        'ᆼ', 'ᆽ', 'ᆾ', 'ᆿ', 'ᇀ', 'ᇁ', 'ᇂ',
        'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㄺ',
        'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅄ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ',
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
        'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
        'ㅣ'
    )

    /**
     * 주어진 문자가 한글 음절인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 한글 음절이면 `true`, 아니면 `false`.
     */
    @JvmStatic
    fun isSyllable(c: Char): Boolean {
        return HANGUL_SYLLABLES_BASE <= c.code && c.code < HANGUL_SYLLABLES_END
    }

    /**
     * 주어진 문자가 Unicode Hangul Jamo 초성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 Unicode Hangul Jamo 초성이면 `true`,
     * 아니면 `false`.
     */
    @JvmStatic
    fun isChoseong(c: Char): Boolean {
        return 0x1100 <= c.code && c.code <= 0x1112
    }

    /**
     * 주어진 문자가 Unicode Hangul Jamo 중성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 Unicode Hangul Jamo 중성이면 `true`,
     * 아니면 `false`.
     */
    fun isJungseong(c: Char): Boolean {
        return 0x1161 <= c.code && c.code <= 0x1175
    }

    /**
     * 주어진 문자가 Unicode Hangul Jamo 종성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 Unicode Hangul Jamo 종성이면 `true`,
     * 아니면 `false`.
     */
    fun isJongseong(c: Char): Boolean {
        return 0x11A8 <= c.code && c.code <= 0x11C2
    }

    /**
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 초성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 Unicode Hangul Compatibility Jamo 초성이면
     * `true`, 아니면 `false`.
     */
    @JvmStatic
    fun isCompatChoseong(c: Char): Boolean {
        val index = Arrays.binarySearch(COMPAT_CHOSEONG_COLLECTION, c)
        return index >= 0
    }

    /**
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 중성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 Unicode Hangul Compatibility Jamo 중성이면
     * `true`, 아니면 `false`.
     */
    fun isCompatJungseong(c: Char): Boolean {
        val index = Arrays.binarySearch(COMPAT_JUNGSEONG_COLLECTION, c)
        return index >= 0
    }

    /**
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 종성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return `c`가 Unicode Hangul Compatibility Jamo 종성이면
     * `true`, 아니면 `false`.
     */
    fun isCompatJongseong(c: Char): Boolean {
        val index = Arrays.binarySearch(COMPAT_JONGSEONG_COLLECTION, c)
        return index >= 0
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Jamo 초성을 추출한다.
     *
     * @param syllable 초성을 추출할 한글 음절
     * @return Unicode Hangul Jamo 초성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
    fun getChoseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        return (0x1100 + getChoseongIndex(syllable)).toChar()
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Jamo 중성을 추출한다.
     *
     * @param syllable 중성을 추출할 한글 음절
     * @return Unicode Hangul Jamo 중성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
    fun getJungseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        return (0x1161 + getJungseongIndex(syllable)).toChar()
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Jamo 종성을 추출한다.
     *
     * @param syllable 종성을 추출할 한글 음절
     * @return Unicode Hangul Jamo 종성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
   /* fun getJongseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        val index = getJongseongIndex(syllable)
        return (if (index == 0) '\u0000' else 0x11A7 + index).toChar()
    }*/

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 초성을 추출한다.
     *
     * @param syllable 초성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 초성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
    fun getCompatChoseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        return COMPAT_CHOSEONG_COLLECTION[getChoseongIndex(syllable)]
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 중성을 추출한다.
     *
     * @param syllable 중성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 중성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
    fun getCompatJungseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        return COMPAT_JUNGSEONG_COLLECTION[getJungseongIndex(syllable)]
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 종성을 추출한다.
     *
     * @param syllable 종성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 종성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
    fun getCompatJongseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        return COMPAT_JONGSEONG_COLLECTION[getJongseongIndex(syllable)]
    }

    /**
     * Unicode Hangul Compatibility Jamo 초성 문자를 Unicode Hangul Jamo 초성 문자로 변환한다.
     *
     * @param c 변환할 Unicode Hangul Compatibility Jamo 초성 문자.
     * @return 변환된 Unicode Hangul Jamo 초성 문자.
     * @throws IllegalArgumentException 주어진 `c`가
     * Unicode Hangul Compatibility Jamo 초성 문자가 아닐 때.
     */
    fun compatChoseongToChoseong(c: Char): Char {
        val index = Arrays.binarySearch(COMPAT_CHOSEONG_COLLECTION, c)
        require(index >= 0) { c.toString() }
        return (0x1100 + index).toChar()
    }

    /**
     * Unicode Hangul Jamo 초성 문자를 Unicode Hangul Compatibility Jamo 초성 문자로 변환한다.
     *
     * @param c 변환할 Unicode Hangul Jamo 초성 문자.
     * @return 변환된 Unicode Hangul Compatibility Jamo 초성 문자.
     * @throws IllegalArgumentException 주어진 `c`가
     * Unicode Hangul Jamo 초성 문자가 아닐 때.
     */
    @JvmStatic
    fun choseongToCompatChoseong(c: Char): Char {
        require(isChoseong(c)) { c.toString() }
        return COMPAT_CHOSEONG_COLLECTION[c.code - 0x1100]
    }

    /**
     * 주어진 자모를 복자모로 결합한다.
     *
     * 복자모가 주어지거나 단자모가 한 개만 주어지면 주어진 자모를 그대로 반환한다.
     *
     * @param jamo 결합할 두 개의 단자모.
     * @return 결합된 한 개의 복자모.
     * @throws IllegalArgumentException 주어진 `jamo`가 한글 자모가 아니거나
     * 두 개를 넘는 자모가 주어질 때.
     */
    fun joinJamo(jamo: String): Char {
        val index = Arrays.binarySearch(JAMO_STRINGS, jamo)
        require(index >= 0) { jamo }
        return JAMO_CHARS[index]
    }

    /**
     * 주어진 자모를 단자모로 분해한다.
     *
     * 복자모가 주어지면 두 개의 단자모로 분해하여 반환하며,
     * 단자모가 주어지면 주어진 단자모를 그대로 반환한다.
     *
     * @param jamo 분해할 복자모.
     * @return 분해된 단자모.
     * @throws IllegalArgumentException 주어진 `jamo`가 한글 자모가 아닐 때.
     */
    @JvmStatic
    fun splitJamo(jamo: Char): String {
        val index = Arrays.binarySearch(JAMO_CHARS, jamo)
        require(index >= 0) { jamo.toString() }
        return JAMO_STRINGS[index]
    }

    /**
     * 주어진 한글 음절을 Unicode Hangul Jamo 초성, 중성, 종성으로 분해한다.
     *
     * @param syllable 분해할 한글 음절.
     * @return 초성, 중성, 종성으로 분해된 문자열 배열. 종성이 없는 음절은 이
     * 배열의 크기가 2이고, 종성이 있는 음절은 3이다.
     * @throws IllegalArgumentException 주어진 `syllable`이 한글 음절이 아닐 때.
     */
 /*   fun decompose(syllable: Char): Array<String> {
        require(isSyllable(syllable)) { syllable.toString() }
        val cho = splitJamo(getChoseong(syllable))
        val jung = splitJamo(getJungseong(syllable))
        val jong = splitJamo(getJongseong(syllable))
        return if (jong.isEmpty()) arrayOf(cho, jung) else arrayOf(
            cho,
            jung,
            jong
        )
    }*/

    /**
     * 주어진 한글 음절을 Unicode Hangul Compatibility Jamo 초성, 중성, 종성으로 분해한다.
     *
     * @param syllable 분해할 한글 음절.
     * @return 초성, 중성, 종성으로 분해된 문자열 배열. 종성이 없는 음절은 이
     * 배열의 크기가 2이고, 종성이 있는 음절은 3이다.
     * @throws IllegalArgumentException 주어진 `syllable`이 한글 음절이 아닐 때.
     */
    @JvmStatic
    fun decomposeCompat(syllable: Char): Array<String> {
        require(isSyllable(syllable)) { syllable.toString() }
        val cho = splitJamo(getCompatChoseong(syllable))
        val jung = splitJamo(getCompatJungseong(syllable))
        val jong = splitJamo(getCompatJongseong(syllable))
        return if (jong.isEmpty()) arrayOf(cho, jung) else arrayOf(
            cho,
            jung,
            jong
        )
    }

    private fun getChoseongIndex(syllable: Char): Int {
        val sylIndex = syllable.code - HANGUL_SYLLABLES_BASE
        return sylIndex / (JUNGSEONG_COUNT * JONGSEONG_COUNT)
    }

    private fun getJungseongIndex(syllable: Char): Int {
        val sylIndex = syllable.code - HANGUL_SYLLABLES_BASE
        return sylIndex % (JUNGSEONG_COUNT * JONGSEONG_COUNT) / JONGSEONG_COUNT
    }

    private fun getJongseongIndex(syllable: Char): Int {
        val sylIndex = syllable.code - HANGUL_SYLLABLES_BASE
        return sylIndex % JONGSEONG_COUNT
    }
}