import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertFailsWith

class BinarySearchTreeTest {
    // INSERT TEST
    @Test
    fun `insertion with existing key should throw IllegalArgumentException`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(10, 1)
        assertFailsWith<IllegalArgumentException> { tree.insert(10, 2) }
        tree.insert(20, 3)
        assertFailsWith<IllegalArgumentException> { tree.insert(20, 3) }
    }

    @Test
    fun `insertion with non-existing key should succeed`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(10, 1)
        tree.insert(15, 2)
        tree.insert(5, 3)
        assertEquals(listOf(5, 10, 15), tree.getKeys())
    }

    // REMOVE TEST
    @Test
    fun `remove node with no children should remove node correctly`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2))
        tree.remove(15)
        assertEquals(listOf(10), tree.getKeys())
    }

    @Test
    fun `remove node with one child should remove node correctly`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 5 to 3))
        tree.remove(10)
        assertEquals(listOf(5, 15), tree.getKeys())
    }

    @Test
    fun `remove root should remove node correctly`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        tree.remove(10)
        assertEquals(listOf(5, 15), tree.getKeys())
    }

    @Test
    fun `remove node with two children should remove node correctly`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(20 to 0, 10 to 1, 15 to 2, 5 to 3))
        tree.remove(10)
        assertEquals(listOf(5, 15, 20), tree.getKeys())
    }

    @Test
    fun `remove a non-existing key should throw NoSuchElementException`() {
        val tree = BinarySearchTree<Int, Int>()
        assertFailsWith<NoSuchElementException> { tree.remove(10) }
        tree.insert(10, 5)
        assertFailsWith<NoSuchElementException> { tree.remove(20) }
    }
}

class TreeSearchWithBinaryTest {
    // SEARCH TEST
    @Test
    fun `search a non-existing key should return null`() {
        val tree = BinarySearchTree<Int, Int>()
        assertEquals(null, tree.search(10))
    }

    @Test
    fun `search a existing key should return value greater than this key`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        assertEquals(1, tree.search(10))
        assertEquals(2, tree.search(15))
        assertEquals(3, tree.search(5))
    }

    // GET VALUES(KEYS) TEST
    @Test
    fun `getKeys(Value) with an empty tree it should return empty list`() {
        val tree = BinarySearchTree<Int, Int>()
        assertEquals(listOf<Int>(), tree.getValues())
        assertEquals(listOf<Int>(), tree.getKeys())
    }

    @Test
    fun `getKeys(Value) with a non-empty tree should return a list with keys(value)`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        assertEquals(listOf(3, 1, 2), tree.getValues())
        assertEquals(listOf(5, 10, 15), tree.getKeys())
    }

    // INSERT SEVERAL KEYS TEST
    @Test
    fun `insert several keys should succeed`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(20 to 1, 5 to 2, 15 to 3))
        assertEquals(listOf(5, 15, 20), tree.getKeys())
    }

    // REMOVE SEVERAL KEYS TEST
    @Test
    fun `remove several keys should succeed`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(20 to 1, 5 to 2, 15 to 3, 25 to 4, 30 to 5))
        tree.remove(listOf(20, 5, 30))
        assertEquals(listOf(15, 25), tree.getKeys())
    }

    // GET MIN(MAX) TEST
    @Test
    fun `getMin(Max) with a empty tree should return a null`() {
        val tree = BinarySearchTree<Int, Int>()
        assertEquals(null, tree.getMinKey())
        assertEquals(null, tree.getMaxKey())
    }

    @Test
    fun `getMin(Max) with a non-empty tree should return a min(max) key`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        assertEquals(5, tree.getMinKey())
        assertEquals(15, tree.getMaxKey())
    }

    // MIN NODE TEST
    @Test
    fun `getMinNode should return a min node`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3, 12 to 4, 20 to 5))
        tree.remove(10)
        assertEquals(listOf(5, 12, 15, 20), tree.getKeys())
    }

    // REPLACE TEST
    @Test
    fun `replace value should succeed`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(20 to 1, 5 to 2, 15 to 3))
        tree.replaceValue(5, 4)
        assertEquals(listOf(4, 3, 1), tree.getValues())
    }

    @Test
    fun `replace a non-existing key should throw NoSuchElementException`() {
        val tree = BinarySearchTree<Int, Int>()
        assertFailsWith<NoSuchElementException> { tree.replaceValue(10, 1) }
        tree.insert(10, 5)
        assertFailsWith<NoSuchElementException> { tree.replaceValue(20, 2) }
    }

    // CLEAN TEST
    @Test
    fun `clean should succeed`() {
        val tree = BinarySearchTree<Int, Int>()
        tree.insert(listOf(0 to 1, 1 to 2, 10 to 3, 15 to 4, 100 to 5, 5 to 6))
        tree.clean()
        assertEquals(listOf<Int>(), tree.getKeys())
    }
}

