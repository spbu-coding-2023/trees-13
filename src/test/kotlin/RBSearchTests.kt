import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertFailsWith

class TreeSearchWithRedBlackTest() {
    // SEARCH TEST
    @Test
    fun `search a non-existing key should return null`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        assertEquals(null, tree.search(10))
    }
    @Test
    fun `search a existing key should return value greater than this key`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        assertEquals(1, tree.search(10))
        assertEquals(2, tree.search(15))
        assertEquals(3, tree.search(5))
    }
    // GET VALUES(KEYS) TEST
    @Test
    fun `getKeys(Value) with an empty tree it should return empty list`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        assertEquals(listOf<Int>(), tree.getValues())
        assertEquals(listOf<Int>(), tree.getKeys())
    }
    @Test
    fun `getKeys(Value) with a non-empty tree should return a list with keys(value)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        assertEquals(listOf(3, 1, 2), tree.getValues())
        assertEquals(listOf(5, 10, 15), tree.getKeys())
    }
    // GET MIN(MAX) TEST
    @Test
    fun `getMin(Max) with a empty tree should return a null)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        assertEquals(null, tree.getMinKey())
        assertEquals(null, tree.getMaxKey())
    }
    @Test
    fun `getMin(Max) with a non-empty tree should return a min(max) key)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
        assertEquals(5, tree.getMinKey())
        assertEquals(15, tree.getMaxKey())
    }
    // MIN NODE TEST
    @Test
    fun `getMinNode should return a min node)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(10 to 1, 15 to 2, 5 to 3, 12 to 4, 20 to 5))
        tree.remove(10)
        assertEquals(listOf(5, 12, 15, 20), tree.getKeys())
    }
    // REMOVE SEVERAL KEYS TEST
    @Test
    fun `remove several keys should succeed)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(20 to 1, 5 to 2, 15 to 3, 25 to 4, 30 to 5))
        tree.remove(listOf(20, 5, 30))
        assertEquals(listOf(15,25), tree.getKeys())
    }
    // INSERT SEVERAL KEYS TEST
    @Test
    fun `insert several keys should succeed)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(20 to 1, 5 to 2, 15 to 3, 25 to 4, 30 to 5))
        assertEquals(listOf(5, 15, 20, 25, 30), tree.getKeys())
    }
    // REPLACE TEST
    @Test
    fun `replace value should succeed)`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(listOf(20 to 1, 5 to 2, 15 to 3))
        tree.replaceValue(5, 4)
        assertEquals(4, tree.search(5))
    }
    @Test
    fun `replace a non-existing key should throw NoSuchElementException`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        assertFailsWith<NoSuchElementException> {tree.replaceValue(10, 1)}
        tree.insert(10, 5)
        assertFailsWith<NoSuchElementException> {tree.replaceValue(20, 2)}
    }
}

class RedBlackTreeSearchTest {
    // INSERT TEST
    @Test
    fun `insertion with existing key should throw IllegalArgumentException`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(10, 1)
        assertFailsWith<IllegalArgumentException> {tree.insert(10, 2)}
        assertFailsWith<IllegalArgumentException> {tree.insert(10, 1)}
    }
    @Test
    fun `insertion node with red parent (1) should succeed`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(15, 1)
        tree.insert(20, 2)
        tree.insert(10, 3)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        tree.insert(12, 4)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(false, tree.root?.leftChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.rightChild!!.isRed)
        assertEquals(listOf(10, 12, 15, 20), tree.getKeys())
    }
    @Test
    fun `insertion node with red parent (2) should succeed`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(15, 1)
        tree.insert(20, 2)
        tree.insert(10, 3)
        tree.insert(5, 4)
        tree.insert(12, 5)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(false, tree.root?.leftChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.leftChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.rightChild?.isRed)
        tree.insert(6, 6)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        assertEquals(false, tree.root?.leftChild?.leftChild?.isRed)
        assertEquals(false, tree.root?.leftChild?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.leftChild?.rightChild?.isRed)
        assertEquals(listOf(5, 6, 10, 12, 15, 20), tree.getKeys())
    }
    @Test
    fun `insertion root should succeed`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(10, 1)
        assertEquals(false, tree.root?.isRed)
        assertEquals(listOf(10), tree.getKeys())
    }
    @Test
    fun `insertion node with red parent and rotate should succeed`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(20, 1)
        tree.insert(10, 2)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        tree.insert(15, 3)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        assertEquals(listOf(10, 15, 20), tree.getKeys())
    }
    @Test
    fun `insertion node with red parent and rotate right should succeed`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(20, 1)
        tree.insert(10, 2)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        tree.insert(5, 3)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        assertEquals(listOf(5, 10, 20), tree.getKeys())
    }
    @Test
    fun `insertion node with black parent should succeed`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        tree.insert(15, 1)
        tree.insert(10, 2)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        tree.insert(20, 3)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        assertEquals(listOf(10, 15, 20), tree.getKeys())
    }
    @Test
    fun `right rotate without brother `() {
        val tree: RedBlackTreeSearch<Int, String> = RedBlackTreeSearch()
        tree.insert(listOf(5 to "node1", 4 to "node2", 3 to "node3", 2 to "node4", 1 to "node5"))
        assertEquals(tree.root?.value, "node2")
        assertEquals(tree.root?.isRed, false)
        assertEquals(tree.root?.leftChild?.value, "node4")
        assertEquals(tree.root?.leftChild?.isRed, false)
        assertEquals(tree.root?.leftChild?.rightChild?.value, "node3")
        assertEquals(tree.root?.leftChild?.rightChild?.isRed, true)
        assertEquals(tree.root?.rightChild?.value, "node1")
        assertEquals(tree.root?.rightChild?.isRed, false)
        assertEquals(tree.root?.leftChild?.leftChild?.value, "node5")
        assertEquals(tree.root?.leftChild?.leftChild?.isRed, true)
    }
    @Test
    fun `left rotate without brother`() {
        val tree : RedBlackTreeSearch<Int,String> = RedBlackTreeSearch()
        tree.insert( listOf( 1 to "node1", 2 to "node2", 3 to "node3", 4 to "node4", 5 to "node5" ) )
        assertEquals(tree.root?.value, "node2")
        assertEquals(tree.root?.isRed, false)
        assertEquals(tree.root?.rightChild?.value, "node4")
        assertEquals(tree.root?.rightChild?.isRed, false)
        assertEquals(tree.root?.rightChild?.rightChild?.value, "node5")
        assertEquals(tree.root?.rightChild?.rightChild?.isRed, true)
        assertEquals(tree.root?.leftChild?.value, "node1")
        assertEquals(tree.root?.leftChild?.isRed, false)
        assertEquals(tree.root?.rightChild?.leftChild?.value, "node3")
        assertEquals(tree.root?.rightChild?.leftChild?.isRed, true)
    }
    @Test
    fun `left rotate not around`() {
        val tree : RedBlackTreeSearch<Int,String> = RedBlackTreeSearch()
        tree.insert( listOf( 1 to "node1", 2 to "node2", 3 to "node3", 4 to "node4", 5 to "node5" ) )
        assertEquals(tree.root?.value, "node2")
        assertEquals(tree.root?.isRed, false)
        assertEquals(tree.root?.rightChild?.value, "node4")
        assertEquals(tree.root?.rightChild?.isRed, false)
        assertEquals(tree.root?.rightChild?.rightChild?.value, "node5")
        assertEquals(tree.root?.rightChild?.rightChild?.isRed, true)
        assertEquals(tree.root?.leftChild?.value, "node1")
        assertEquals(tree.root?.leftChild?.isRed, false)
        assertEquals(tree.root?.rightChild?.leftChild?.value, "node3")
        assertEquals(tree.root?.rightChild?.leftChild?.isRed, true)
    }
    @Test
    fun `red father and red uncle with recursion`() {
        val tree : RedBlackTreeSearch<Int,String> = RedBlackTreeSearch()
        tree.insert( listOf( 10 to "node1", 20 to "node2", 30 to "node3", 40 to "node4", 50 to "node5", 25 to "node6" ) )
        assertEquals(tree.root?.value, "node2")
        assertEquals(tree.root?.isRed, false)
        assertEquals(tree.root?.rightChild?.value, "node4")
        assertEquals(tree.root?.rightChild?.isRed, true)
        assertEquals(tree.root?.rightChild?.rightChild?.value, "node5")
        assertEquals(tree.root?.rightChild?.rightChild?.isRed, false)
        assertEquals(tree.root?.leftChild?.value, "node1")
        assertEquals(tree.root?.leftChild?.isRed, false)
        assertEquals(tree.root?.rightChild?.leftChild?.value, "node3")
        assertEquals(tree.root?.rightChild?.leftChild?.isRed, false)
        assertEquals(tree.root?.rightChild?.leftChild?.leftChild?.value, "node6")
        assertEquals(tree.root?.rightChild?.leftChild?.leftChild?.isRed, true)
    }
    // REMOVE TEST
    @Test
    fun `remove a non-existing key should throw NoSuchElementException`() {
        val tree = RedBlackTreeSearch<Int, Int>()
        assertFailsWith<NoSuchElementException> {tree.remove(10)}
        tree.insert(10, 5)
        assertFailsWith<NoSuchElementException> {tree.remove(20)}
    }
    @Test
    fun `remove red node without children (1) should remove node correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 20 to 3, 5 to 4))
        tree.remove(5)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.leftChild?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(listOf(10, 15, 20), tree.getKeys())
    }
    @Test
    fun `remove red node without children (2) should remove node correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 20 to 3))
        tree.remove(10)
        assertEquals(false, tree.root?.isRed)
        assertEquals(true, tree.root?.rightChild?.isRed)
        assertEquals(listOf(15, 20), tree.getKeys())
    }
    @Test
    fun `remove black node with one red child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 20 to 3, 5 to 4))
        tree.remove(10)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.leftChild?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(listOf(5, 15, 20), tree.getKeys())
        tree.insert(10, 5)
        tree.remove(5)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.leftChild?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
    }
    @Test
    fun `remove black node without child and black brother with red right child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4, 30 to 5))
        tree.remove(10)
        assertEquals(false, tree.root?.isRed)
        assertEquals(false, tree.root?.leftChild?.isRed)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.rightChild?.isRed)
        assertEquals(listOf(15, 20, 25, 30), tree.getKeys())
    }
    @Test
    fun `remove black node without child and black brother with red left child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4))
        tree.remove(10)
        assertEquals(listOf(15, 20, 25), tree.getKeys())
    }
    @Test
    fun `remove black node without child and black brother without child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 25 to 3))
        tree.root?.leftChild?.isRed = false
        tree.root?.rightChild?.isRed = false
        tree.remove(10)
        assertEquals(true, tree.root?.rightChild?.isRed)
        assertEquals(false, tree.root?.isRed)
        assertEquals(listOf(15, 25), tree.getKeys())
    }
    @Test
    fun `remove black node with black parent and without child and black brother without child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
        tree.root?.rightChild?.rightChild?.isRed = false
        tree.root?.leftChild?.rightChild?.isRed = false
        tree.root?.rightChild?.leftChild?.isRed = false
        tree.root?.leftChild?.leftChild?.isRed = false
        tree.remove(4)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.rightChild?.leftChild?.isRed)
        assertEquals(listOf(0, 1, 2, 3, 5, 6), tree.getKeys())
    }
    @Test
    fun `remove black node with red parent and without child and black brother without child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
        tree.root?.rightChild?.isRed = true
        tree.root?.leftChild?.isRed = true
        tree.root?.rightChild?.rightChild?.isRed = false
        tree.root?.leftChild?.rightChild?.isRed = false
        tree.root?.rightChild?.leftChild?.isRed = false
        tree.root?.leftChild?.leftChild?.isRed = false
        tree.remove(4)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.rightChild?.rightChild?.isRed)
        assertEquals(listOf(0, 1, 2, 3, 5, 6), tree.getKeys())
    }
    @Test
    fun `remove black node without child and red brother without child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4, 30 to 5))
        tree.root?.rightChild?.isRed = true
        tree.root?.rightChild?.rightChild?.isRed = false
        tree.root?.rightChild?.leftChild?.isRed = false
        tree.remove(10)
        assertEquals(false, tree.root?.rightChild?.isRed)
        assertEquals(true, tree.root?.leftChild?.isRed)
        assertEquals(false, tree.root?.leftChild?.rightChild?.isRed)
        assertEquals(listOf(15, 20, 25, 30), tree.getKeys())
    }
    @Test
    fun `remove node with 2 child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4, 30 to 5, 45 to 6, 40 to 7))
        tree.remove(10)
        assertEquals(listOf(15, 20, 25, 30, 40, 45), tree.getKeys())
    }
    @Test
    fun `remove root with 2 child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(15 to 1, 10 to 2, 25 to 3))
        tree.remove(15)
        assertEquals(listOf(10, 25), tree.getKeys())
    }
    @Test
    fun `remove root with 2 child with child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
        tree.remove(3)
        assertEquals(listOf(0, 1, 2, 4, 5, 6), tree.getKeys())
    }
    @Test
    fun `remove root without child should correctly`() {
        val tree = RedBlackTreeSearch<Int,Int>()
        tree.insert(15, 1)
        tree.remove(15)
        assertEquals(listOf<Int>(), tree.getKeys())
    }
}