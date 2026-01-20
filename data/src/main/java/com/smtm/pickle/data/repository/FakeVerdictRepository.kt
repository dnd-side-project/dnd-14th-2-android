package com.smtm.pickle.data.repository

import com.smtm.pickle.domain.model.common.PaginatedResult
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.domain.repository.VerdictRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VerdictRepository의 Fake 구현
 *
 * 용도:
 * - Retrofit/서버 연동 전 UI 개발
 * - 페이지네이션 동작 테스트
 * - 다양한 상태(로딩, 에러) 시뮬레이션
 *
 * 나중에 서버 연동 시:
 * - VerdictRepositoryImpl 생성
 * - RepositoryModule에서 바인딩 교체
 */
@Singleton
class FakeVerdictRepository @Inject constructor() : VerdictRepository {

    // ========================================
    // 가짜 데이터 (총 35개 - 페이지네이션 테스트용)
    // ========================================

    private val allFakeData: List<VerdictRequest> by lazy {
        generateFakeData()
    }

    // ========================================
    // Repository 구현
    // ========================================

    override suspend fun getVerdictRequests(
        filter: VerdictRequestFilter,
        page: Int,
        size: Int
    ): Result<PaginatedResult<VerdictRequest>> {

        // 1. 네트워크 지연 시뮬레이션 (500ms ~ 1500ms)
        //    → 로딩 UI 테스트 가능
        delay((500L..1500L).random())

        // 2. 에러 시뮬레이션 (필요시 주석 해제)
        // if (page == 2) {
        //     return Result.failure(Exception("페이지 2 로드 실패 (테스트)"))
        // }

        return runCatching {
            // 3. 필터 적용
            val filteredData = when (filter) {
                VerdictRequestFilter.All -> allFakeData
                VerdictRequestFilter.Pending ->
                    allFakeData.filter { it.status == VerdictStatus.Pending }
                VerdictRequestFilter.Completed ->
                    allFakeData.filter { it.status == VerdictStatus.Completed }
            }

            // 4. 페이지네이션 계산
            val startIndex = page * size
            val endIndex = minOf(startIndex + size, filteredData.size)

            // 5. 범위 초과 시 빈 결과
            if (startIndex >= filteredData.size) {
                return@runCatching PaginatedResult(
                    items = emptyList(),
                    hasMore = false,
                    totalCount = filteredData.size
                )
            }

            // 6. 페이지 데이터 추출
            val pageItems = filteredData.subList(startIndex, endIndex)
            val hasMore = endIndex < filteredData.size

            PaginatedResult(
                items = pageItems,
                hasMore = hasMore,
                totalCount = filteredData.size
            )
        }
    }

    override suspend fun getRequestedStatus(): Result<VerdictRequestedStatus> {
        // 네트워크 지연 시뮬레이션
        delay((200L..500L).random())

        return runCatching {
            // 배너 테스트를 위해 항상 Requested 반환
            // None으로 바꾸면 배너가 다르게 표시됨
            VerdictRequestedStatus.Requested
        }
    }

    // ========================================
    // 가짜 데이터 생성
    // ========================================

    private fun generateFakeData(): List<VerdictRequest> {
        val nicknames = listOf(
            "피클러", "결정장애극복", "소비요정", "짠돌이", "플렉스왕",
            "알뜰살뜰", "현명한소비", "충동구매러", "가성비헌터", "럭셔리러버"
        )

        val badges = listOf(
            "초보 심판", "베테랑 심판", "전설의 심판", "신규 회원",
            "활발한 참여자", "공정한 심판", "인기 심판관"
        )

        // 35개 데이터 생성
        // - Pending: 24개 (index % 3 != 0)
        // - Completed: 11개 (index % 3 == 0)
        return (1..35).map { index ->
            VerdictRequest(
                id = index.toLong(),
                userId = "user_${index.toString().padStart(3, '0')}",
                nickname = "${nicknames[index % nicknames.size]}$index",
                badge = badges[index % badges.size],
                profileImage = "https://picsum.photos/seed/$index/200",
                status = if (index % 3 == 0) VerdictStatus.Completed else VerdictStatus.Pending,
                joinedCount = (1..50).random()
            )
        }
    }
}