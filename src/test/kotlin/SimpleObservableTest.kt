import com.google.common.truth.Truth.assertThat
import io.reactivex.Observable
import org.junit.Test

class SimpleObservableTest {
    @Test
    fun `GIVEN testObserver with items WHEN take 1 THEN just 1 `() {
        val testObserver = Observable.just(1, 2, 3, 4).take(1).test()
        assertThat(testObserver.valueCount()).isEqualTo(1)
        assertThat(testObserver.errorCount()).isEqualTo(0)
        testObserver.assertValue(1)
    }

    @Test
    fun `GIVEN testObserver with no items WHEN take 1 and singleOrError THEN error`() {
        val testObserver = Observable.empty<String>().take(1).singleOrError().test()
        assertThat(testObserver.valueCount()).isEqualTo(0)
        assertThat(testObserver.errorCount()).isEqualTo(1)
    }

    @Test
    fun `GIVEN testObserver with no items WHEN take 1 and single with default THEN 1 item`() {
        val testObserver = Observable.empty<String>().take(1).single("default").test()
        assertThat(testObserver.valueCount()).isEqualTo(1)
        assertThat(testObserver.errorCount()).isEqualTo(0)
        testObserver.assertValue("default")
    }

    @Test
    fun `GIVEN testObserver with many items WHEN take 1 and single with default THEN error`() {
        val testObserver = Observable.just(1, 2, 3, 4).single(0).test()
        assertThat(testObserver.valueCount()).isEqualTo(0)
        assertThat(testObserver.errorCount()).isEqualTo(1)
        testObserver.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `GIVEN testObserver with many items WHEN take 1 and first THEN 1 item`() {
        val testObserver = Observable.just(1, 2, 3, 4).first(0).test()
        assertThat(testObserver.valueCount()).isEqualTo(1)
        assertThat(testObserver.errorCount()).isEqualTo(0)
    }
}