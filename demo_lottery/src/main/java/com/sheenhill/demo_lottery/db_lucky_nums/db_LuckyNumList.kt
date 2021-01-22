package com.sheenhill.demo_lottery.db_lucky_nums

import android.content.Context
import androidx.room.*
import java.util.*

@Entity
data class LuckyNums(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "date") val date: Date,
        @ColumnInfo(name = "numList") val numList: List<String>
)

@Dao
interface LuckyNumsDao{
    @Query("SELECT * FROM LuckyNums")
    fun getAll(): List<LuckyNums>

    @Query("SELECT * FROM LuckyNums WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<LuckyNums>

    @Query("SELECT * FROM LuckyNums WHERE date LIKE date")
    fun findByDate(date: Date): LuckyNums

    @Insert
    fun insertAll(vararg nums: LuckyNums)

    @Delete
    fun delete(nums: LuckyNums)
}


@Database(entities = arrayOf(LuckyNums::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun luckyNumsDao(): LuckyNumsDao
}

fun getLuckyNumsDB(applicationContext:Context):AppDatabase{
    return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
    ).build()
}

/* 测试 */
//@RunWith(AndroidJUnit4::class)
//class SimpleEntityReadWriteTest {
//    private lateinit var luckyNumsDao: LuckyNumsDao
//    private lateinit var db: AppDatabase
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//                context, AppDatabase::class.java).build()
//        userDao = db.getUserDao()
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun writeUserAndReadInList() {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
//        userDao.insert(user)
//        val byName = userDao.findUsersByName("george")
//        assertThat(byName.get(0), equalTo(user))
//    }
//}