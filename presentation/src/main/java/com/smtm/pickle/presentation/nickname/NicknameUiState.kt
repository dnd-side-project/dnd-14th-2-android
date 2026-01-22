package com.smtm.pickle.presentation.nickname

/**
 * 닉네임 관련 UI 상태
 * @property nickname 닉네임
 * @property isValidFormat 닉네임 형식 유효 여부
 * @property isCheckingDuplicate 중복 확인 중 여부
 * @property isAvailable 중복 확인 결과
 */
data class NicknameUiState(
    val nickname: String = "",
    val isValidFormat: Boolean = true,
    val isCheckingDuplicate: Boolean = false,
    val isAvailable: Boolean? = null,
) {
    /** 텍스트 필드 오류 인터렉션용 */
    val isError: Boolean
        get() = nickname.isNotEmpty() && !isValidFormat

    /** 버튼 활성화 여부 */
    val canSubmit: Boolean
        get() = isValidFormat && isAvailable == true
}
