package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import javax.persistence.*


@Entity
class UserLoanHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val user: User,

    val bookName: String,

    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = UserLoanStatus.LOANED
){

    fun doReturn() {
        status = UserLoanStatus.RETURNED
    }

    fun isReturn(): Boolean {
        return status == UserLoanStatus.RETURNED
    }

    companion object {
        fun fixture(
            user: User,
            bookName: String = "이상한 나라의 엘리스",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null,
        ): UserLoanHistory {
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id,
            )
        }
    }

}