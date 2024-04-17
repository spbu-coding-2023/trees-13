import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertFailsWith


class AVLTreeMethodTest {
    //CHECK INSERT
    @Test
    fun `insertion with existing key and value should throw IllegalArgumentException`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(5, "value")
        assertFailsWith<IllegalArgumentException> {
            avlTree.insert(5, "value")
        }
    }

    @Test
    fun `insertion with non-existing key should succeed`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(5, "value")
        assertEquals("value", avlTree.search(5))
    }

    @Test
    fun `insertion with existing key should throw IllegalArgumentException`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(5, "value")
        assertFailsWith<IllegalArgumentException> {
            avlTree.insert(5, "new value")
        }
    }

    //check insert for list
    @Test
    fun `insert list of pairs should succeed`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(listOf(1 to "one", 2 to "two", 3 to "three"))
        assertEquals("one", avlTree.search(1))
        assertEquals("two", avlTree.search(2))
        assertEquals("three", avlTree.search(3))
    }

    @Test
    fun `inserting duplicate keys should throw IllegalArgumentException`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(listOf(1 to "one", 2 to "two"))
        assertFailsWith<IllegalArgumentException> {
            avlTree.insert(2, "new two")
        }
    }

    @Test
    fun `inserting duplicate keys with different values should throw IllegalArgumentException`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(listOf(1 to "one", 2 to "two"))
        assertFailsWith<IllegalArgumentException> {
            avlTree.insert(2, "two")
        }
    }

    //check leftRotate
    @Test
    fun `leftRotate should rotate tree to the left correctly in insert`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "ten")
        avlTree.insert(20, "twenty")
        avlTree.insert(30, "thirty")
        assertEquals(20, avlTree.root!!.key)
        assertEquals(10, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals("ten", avlTree.search(10))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    @Test
    fun `leftRotate balancing test in which the node has a child`() {
        val avlTree = AVLTreeSearch<Int, Int>()
        avlTree.insert(listOf(1 to 1, 0 to 0, 3 to 3, 2 to 2, 4 to 4, 5 to 5))
        assertEquals(3, avlTree.root!!.key)
        assertEquals(1, avlTree.root!!.leftChild!!.key)
        assertEquals(0, avlTree.root!!.leftChild!!.leftChild!!.key)
        assertEquals(2, avlTree.root!!.leftChild!!.rightChild!!.key)
        assertEquals(4, avlTree.root!!.rightChild!!.key)
        assertEquals(5, avlTree.root!!.rightChild!!.rightChild!!.key)
    }

    //check rightRotate
    @Test
    fun `rightRotate should rotate tree to the right correctly in insert`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(30, "thirty")
        avlTree.insert(20, "twenty")
        avlTree.insert(10, "ten")
        assertEquals(20, avlTree.root!!.key)
        assertEquals(10, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals("ten", avlTree.search(10))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    @Test
    fun `rightRotate balancing test in which the node has a child`() {
        val avlTree = AVLTreeSearch<Int, Int>()
        avlTree.insert(listOf(4 to 4, 2 to 2, 5 to 5, 3 to 3, 1 to 1, 0 to 0))
        assertEquals(2, avlTree.root!!.key)
        assertEquals(1, avlTree.root!!.leftChild!!.key)
        assertEquals(0, avlTree.root!!.leftChild!!.leftChild!!.key)
        assertEquals(4, avlTree.root!!.rightChild!!.key)
        assertEquals(3, avlTree.root!!.rightChild!!.leftChild!!.key)
        assertEquals(5, avlTree.root!!.rightChild!!.rightChild!!.key)
    }

    //check rightLeftRotate
    @Test
    fun `rightleftRotate should rotate tree to the right correctly in insert`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "ten")
        avlTree.insert(30, "thirty")
        avlTree.insert(20, "twenty")
        assertEquals(20, avlTree.root!!.key)
        assertEquals(10, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals("ten", avlTree.search(10))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    //check leftrightRotate
    @Test
    fun `leftrightRotate should rotate tree to the right correctly in insert`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(30, "thirty")
        avlTree.insert(10, "ten")
        avlTree.insert(20, "twenty")
        assertEquals(20, avlTree.root!!.key)
        assertEquals(10, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals("ten", avlTree.search(10))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    //CHECK REMOVE
    @Test
    fun `remove node with no children should remove node correctly`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.remove(10)
        assertEquals(null, avlTree.search(10))
    }

    @Test
    fun `remove node with one child should remove node correctly`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "leftChild")
        avlTree.remove(10)
        assertEquals(null, avlTree.search(10))
        assertEquals("leftChild", avlTree.search(5))
    }

    @Test
    fun `remove node with two children should remove node correctly`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "leftChild")
        avlTree.insert(15, "rightChild")
        avlTree.remove(10)
        assertEquals(null, avlTree.search(10))
        assertEquals("leftChild", avlTree.search(5))
        assertEquals("rightChild", avlTree.search(15))
    }

    //check leftRotate
    @Test
    fun `leftRotate should rotate tree correctly with three vertex in remove`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(20, "twenty")
        avlTree.insert(10, "ten")
        avlTree.insert(30, "thirty")
        avlTree.insert(40, "forty")
        avlTree.remove(10)
        assertEquals(30, avlTree.root!!.key)
        assertEquals(20, avlTree.root!!.leftChild!!.key)
        assertEquals(40, avlTree.root!!.rightChild!!.key)
        assertEquals("forty", avlTree.search(40))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    @Test
    fun `leftRotate should rotate tree correctly with four vertex in remove`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(20, "twenty")
        avlTree.insert(10, "ten")
        avlTree.insert(30, "thirty")
        avlTree.insert(40, "forty")
        avlTree.insert(25, "twenty five")
        avlTree.remove(10)
        assertEquals(30, avlTree.root!!.key)
        assertEquals(20, avlTree.root!!.leftChild!!.key)
        assertEquals(40, avlTree.root!!.rightChild!!.key)
        assertEquals(25, avlTree.root!!.leftChild!!.rightChild!!.key)
        assertEquals("forty", avlTree.search(40))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
        assertEquals("twenty five", avlTree.search(25))
    }

    //check rightRotate
    @Test
    fun `rightRotate should rotate tree correctly with three vertex in insert`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(30, "thirty")
        avlTree.insert(20, "twenty")
        avlTree.insert(10, "ten")
        avlTree.insert(40, "forty")
        avlTree.remove(40)
        assertEquals(20, avlTree.root!!.key)
        assertEquals(10, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals("ten", avlTree.search(10))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    @Test
    fun `rightRotate should rotate tree correctly with four vertex in remove`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(30, "thirty")
        avlTree.insert(10, "ten")
        avlTree.insert(40, "forty")
        avlTree.insert(20, "twenty")
        avlTree.insert(5, "five")
        avlTree.remove(40)
        assertEquals(10, avlTree.root!!.key)
        assertEquals(5, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals(20, avlTree.root!!.rightChild!!.leftChild!!.key)
        assertEquals("five", avlTree.search(5))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
        assertEquals("ten", avlTree.search(10))

    }

    //check rightLeftRotate
    @Test
    fun `rightleftRotate should rotate tree correctly in remove`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(20, "twenty")
        avlTree.insert(10, "ten")
        avlTree.insert(40, "forty")
        avlTree.insert(30, "thirty")
        avlTree.remove(10)
        assertEquals(30, avlTree.root!!.key)
        assertEquals(20, avlTree.root!!.leftChild!!.key)
        assertEquals(40, avlTree.root!!.rightChild!!.key)
        assertEquals("forty", avlTree.search(40))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    //check leftrightRotate
    @Test
    fun `leftrightRotate should rotate tree correctly in remove`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(30, "thirty")
        avlTree.insert(10, "ten")
        avlTree.insert(40, "forty")
        avlTree.insert(20, "twenty")
        avlTree.remove(40)
        assertEquals(20, avlTree.root!!.key)
        assertEquals(10, avlTree.root!!.leftChild!!.key)
        assertEquals(30, avlTree.root!!.rightChild!!.key)
        assertEquals("ten", avlTree.search(10))
        assertEquals("twenty", avlTree.search(20))
        assertEquals("thirty", avlTree.search(30))
    }

    //check remove empty tree
    @Test
    fun `Empty tree`() {
        val avlTree = AVLTreeSearch<Int, String>()
        assertFailsWith<NoSuchElementException> {
            avlTree.remove(10)
        }
    }

    //check remove for list
    @Test
    fun `remove list of pairs should remove all pairs correctly`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(listOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five"))
        avlTree.remove(listOf(1, 2, 3))
        assertEquals(null, avlTree.search(1))
        assertEquals(null, avlTree.search(2))
        assertEquals(null, avlTree.search(3))
        assertEquals("four", avlTree.search(4))
        assertEquals("five", avlTree.search(5))
    }

    //check remove with use minNode
    @Test
    fun `remove with use minNode`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(5, "Value5")
        avlTree.insert(3, "Value3")
        avlTree.insert(7, "Value7")
        avlTree.insert(2, "Value2")
        avlTree.insert(4, "Value4")
        avlTree.insert(6, "Value6")
        avlTree.insert(8, "Value8")
        avlTree.remove(5)
        assertEquals(listOf("Value2", "Value3", "Value4", "Value6", "Value7", "Value8"), avlTree.getValues())
    }
}

class AVLTreeSearchTest {
    //check search
    @Test
    fun `search should return correct value for existing key`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        assertEquals("value", avlTree.search(10))
        assertEquals("left", avlTree.search(5))
        assertEquals("right", avlTree.search(15))
    }

    @Test
    fun `search should return null for non-existing key`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        assertEquals(null, avlTree.search(20))
    }

    //check getValues
    @Test
    fun `getValues should return values in inorder traversal`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        assertEquals(listOf("left", "value", "right"), avlTree.getValues())
    }

    //check getKeys
    @Test
    fun `getKeys should return keys in inorder traversal`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        assertEquals(listOf(5, 10, 15), avlTree.getKeys())
    }

    //check getMaxKey
    @Test
    fun `getMaxKey should return maximum key`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        assertEquals(15, avlTree.getMaxKey())
    }

    @Test
    fun `getMaxKey should return null for empty tree`() {
        val avlTree = AVLTreeSearch<Int, String>()
        assertEquals(null, avlTree.getMaxKey())
    }

    //check getMinKey
    @Test
    fun `getMinKey should return minimum key`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        assertEquals(5, avlTree.getMinKey())
    }

    @Test
    fun `getMinKey should return null for empty tree`() {
        val avlTree = AVLTreeSearch<Int, String>()
        assertEquals(null, avlTree.getMinKey())
    }

    //check replaceValue
    @Test
    fun `test ReplaceValue`() {
        val avlTree = AVLTreeSearch<Int, String>()
        avlTree.insert(10, "value")
        avlTree.insert(5, "left")
        avlTree.insert(15, "right")
        avlTree.replaceValue(10, "NewValue")
        assertEquals("NewValue", avlTree.search(10))
        assertFailsWith<NoSuchElementException> {
            avlTree.replaceValue(4, "NewValue")
        }
    }

    // CLEAN TEST
    @Test
    fun `clean should succeed`() {
        val tree = AVLTreeSearch<Int, Int>()
        tree.insert(listOf(0 to 1, 1 to 2, 10 to 3, 15 to 4, 100 to 5, 5 to 6))
        tree.clean()
        assertEquals(listOf<Int>(), tree.getKeys())
    }
}
