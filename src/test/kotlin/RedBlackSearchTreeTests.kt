import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertFailsWith

class RedBlackSearchTreeTest {
  //region INSERT TEST
  @Test
  fun `insertion with existing key should throw IllegalArgumentException`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(10, 1)
    assertFailsWith<IllegalArgumentException> { tree.insert(10, 2) }
    assertFailsWith<IllegalArgumentException> { tree.insert(10, 1) }
  }

  @Test
  fun `insertion node with red parent (1) should succeed`() {
    val tree = RedBlackSearchTree<Int, Int>()
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
    val tree = RedBlackSearchTree<Int, Int>()
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
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(10, 1)
    assertEquals(false, tree.root?.isRed)
    assertEquals(listOf(10), tree.getKeys())
  }

  @Test
  fun `insertion node with red parent and rotate should succeed`() {
    val tree = RedBlackSearchTree<Int, Int>()
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
    val tree = RedBlackSearchTree<Int, Int>()
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
    val tree = RedBlackSearchTree<Int, Int>()
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
    val tree: RedBlackSearchTree<Int, String> = RedBlackSearchTree()
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
    val tree: RedBlackSearchTree<Int, String> = RedBlackSearchTree()
    tree.insert(listOf(1 to "node1", 2 to "node2", 3 to "node3", 4 to "node4", 5 to "node5"))
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
    val tree: RedBlackSearchTree<Int, String> = RedBlackSearchTree()
    tree.insert(listOf(1 to "node1", 2 to "node2", 3 to "node3", 4 to "node4", 5 to "node5"))
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
    val tree: RedBlackSearchTree<Int, String> = RedBlackSearchTree()
    tree.insert(listOf(10 to "node1", 20 to "node2", 30 to "node3", 40 to "node4", 50 to "node5", 25 to "node6"))
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
  //endregion
  //region LEFT ROTATE TEST
  @Test
  fun `leftRotate should rotate tree to the left correctly in insert`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(10, 1)
    tree.insert(20, 2)
    tree.insert(30, 3)
    assertEquals(20, tree.root!!.key)
    assertEquals(10, tree.root!!.leftChild!!.key)
    assertEquals(30, tree.root!!.rightChild!!.key)
    assertEquals(1, tree.search(10))
    assertEquals(2, tree.search(20))
    assertEquals(3, tree.search(30))
  }

  @Test
  fun `leftRotate with two children of the right child`() {
    val tree = RedBlackSearchTree<Int, Int>()
    // preparing the tree for the removing
    tree.insert(listOf(40 to 40, 20 to 20, 60 to 60, 65 to 65, 68 to 68, 70 to 70, 63 to 63, 55 to 55))
    // removing black node without children -> balancing
    tree.remove(20)
    assertEquals(65, tree.root?.key)
    val left = tree.root?.leftChild
    val right = tree.root?.rightChild
    assertEquals(60, left?.key)
    assertEquals(63, left?.rightChild?.key)
    assertEquals(68, right?.key)
    assertEquals(70, right?.rightChild?.key)
    assertEquals(40, left?.leftChild?.key)
    assertEquals(55, left?.leftChild?.rightChild?.key)
  }
  //endregion

  //region RIGHT ROTATE TEST
  @Test
  fun `rightRotate should rotate tree to the right correctly in insert`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(30, 3)
    tree.insert(20, 2)
    tree.insert(10, 1)
    assertEquals(20, tree.root?.key)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(30, tree.root?.rightChild?.key)
    assertEquals(1, tree.search(10))
    assertEquals(2, tree.search(20))
    assertEquals(3, tree.search(30))
  }

  @Test
  fun `rightRotate with two children of the left child`() {
    val tree = RedBlackSearchTree<Int, Int>()
    // preparing the tree for the removing
    tree.insert(listOf(40 to 40, 60 to 60, 20 to 20, 15 to 15, 12 to 12, 10 to 10, 17 to 17, 30 to 30))
    // removing black node without children -> balancing
    tree.remove(60)
    assertEquals(listOf(10, 12, 15, 17, 20, 30, 40), tree.getKeys())
    assertEquals(15, tree.root?.key)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(40, tree.root?.rightChild?.rightChild?.key)
    assertEquals(17, tree.root?.rightChild?.leftChild?.key)
    assertEquals(30, tree.root?.rightChild?.rightChild?.leftChild?.key)
    assertEquals(12, tree.root?.leftChild?.key)
    assertEquals(10, tree.root?.leftChild?.leftChild?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(true, tree.root?.rightChild?.isRed)
    assertEquals(false, tree.root?.rightChild?.rightChild?.isRed)
    assertEquals(false, tree.root?.rightChild?.leftChild?.isRed)
    assertEquals(true, tree.root?.rightChild?.rightChild?.leftChild?.isRed)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(true, tree.root?.leftChild?.leftChild?.isRed)
  }
  //endregion

  //region RIGHT-LEFT ROTATE
  @Test
  fun `right-left rotate should rotate tree to the right correctly in insert`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(10, 1)
    tree.insert(30, 3)
    tree.insert(20, 2)
    assertEquals(20, tree.root?.key)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(30, tree.root?.rightChild?.key)
    assertEquals(1, tree.search(10))
    assertEquals(2, tree.search(20))
    assertEquals(3, tree.search(30))
  }
  //endregion

  //region LEFT-RIGHT ROTATE TEST
  @Test
  fun `left-right rotate should rotate tree to the right correctly in insert`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(30, 3)
    tree.insert(10, 1)
    tree.insert(20, 2)
    assertEquals(20, tree.root?.key)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(30, tree.root?.rightChild?.key)
    assertEquals(1, tree.search(10))
    assertEquals(2, tree.search(20))
    assertEquals(3, tree.search(30))
  }
  //endregion

  //region REMOVE TEST
  @Test
  fun `remove a black node without children and without a sibling should throw an exception`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(10 to 1, 5 to 2))
    tree.root!!.leftChild!!.isRed = false
    assertFailsWith<RuntimeException> { tree.remove(5) }
  }

  @Test
  fun `remove a non-existing key should throw NoSuchElementException`() {
    val tree = RedBlackSearchTree<Int, Int>()
    assertFailsWith<NoSuchElementException> { tree.remove(10) }
    tree.insert(10, 5)
    assertFailsWith<NoSuchElementException> { tree.remove(20) }
  }

  @Test
  fun `remove red node without children (1) should remove node correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 20 to 3, 5 to 4))
    tree.remove(5)
    assertEquals(false, tree.root?.isRed)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.key)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(listOf(10, 15, 20), tree.getKeys())
  }

  @Test
  fun `remove red node without children (2) should remove node correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 20 to 3))
    tree.remove(10)
    assertEquals(false, tree.root?.isRed)
    assertEquals(true, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.key)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(listOf(15, 20), tree.getKeys())
  }

  @Test
  fun `remove black node with one red left child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 20 to 3, 5 to 4))
    tree.remove(10)
    assertEquals(false, tree.root?.isRed)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.key)
    assertEquals(5, tree.root?.leftChild?.key)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(listOf(5, 15, 20), tree.getKeys())
  }

  @Test
  fun `remove black node with one red right child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 5 to 2, 20 to 3, 10 to 4))
    tree.remove(5)
    assertEquals(false, tree.root?.isRed)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.key)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(listOf(10, 15, 20), tree.getKeys())
  }

  @Test
  fun `remove node with 2 child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4, 30 to 5, 45 to 6, 40 to 7))
    tree.remove(10)
    assertEquals(25, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(40, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(20, tree.root?.leftChild?.rightChild?.key)
    assertEquals(true, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(30, tree.root?.rightChild?.leftChild?.key)
    assertEquals(true, tree.root?.rightChild?.leftChild?.isRed)
    assertEquals(45, tree.root?.rightChild?.rightChild?.key)
    assertEquals(true, tree.root?.rightChild?.rightChild?.isRed)
    assertEquals(listOf(15, 20, 25, 30, 40, 45), tree.getKeys())
  }

  @Test
  fun `remove root with 2 child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 25 to 3))
    tree.remove(15)
    assertEquals(25, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(true, tree.root?.leftChild?.isRed)
    assertEquals(listOf(10, 25), tree.getKeys())
  }

  @Test
  fun `remove root with 2 child with child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
    tree.remove(3)
    assertEquals(4, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(5, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(1, tree.root?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(2, tree.root?.leftChild?.rightChild?.key)
    assertEquals(true, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(0, tree.root?.leftChild?.leftChild?.key)
    assertEquals(true, tree.root?.leftChild?.leftChild?.isRed)
    assertEquals(6, tree.root?.rightChild?.rightChild?.key)
    assertEquals(true, tree.root?.rightChild?.rightChild?.isRed)
    assertEquals(listOf(0, 1, 2, 4, 5, 6), tree.getKeys())
  }

  @Test
  fun `remove root without child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(15, 1)
    tree.remove(15)
    assertEquals(listOf<Int>(), tree.getKeys())
  }

  @Test
  fun `remove black node without child and black right brother with red right child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4, 30 to 5))
    tree.remove(10)
    assertEquals(30, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(25, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(20, tree.root?.leftChild?.rightChild?.key)
    assertEquals(true, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(listOf(15, 20, 25, 30), tree.getKeys())
  }

  @Test
  fun `remove black node without child and black left brother with red left child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(20 to 1, 10 to 2, 25 to 3, 15 to 4, 5 to 5))
    tree.remove(25)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(5, tree.root?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(10, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(15, tree.root?.rightChild?.leftChild?.key)
    assertEquals(true, tree.root?.rightChild?.leftChild?.isRed)
    assertEquals(listOf(5, 10, 15, 20), tree.getKeys())
  }

  @Test
  fun `remove black node without child and right black brother with red left child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4))
    tree.remove(10)
    assertEquals(25, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(20, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(listOf(15, 20, 25), tree.getKeys())
  }

  @Test
  fun `remove black node without child and left black brother with red right child should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(10 to 1, 20 to 2, 25 to 3, 15 to 4))
    tree.remove(25)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(10, tree.root?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(15, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(listOf(10, 15, 20), tree.getKeys())
  }

  @Test
  fun `remove black node without child and black brother without child with root-parent should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 25 to 3))
    tree.root?.leftChild?.isRed = false
    tree.root?.rightChild?.isRed = false
    tree.remove(10)
    assertEquals(25, tree.root?.rightChild?.key)
    assertEquals(true, tree.root?.rightChild?.isRed)
    assertEquals(15, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(listOf(15, 25), tree.getKeys())
  }

  @Test
  fun `remove black node without child and black right brother without child with black parent should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
    tree.root?.rightChild?.rightChild?.isRed = false
    tree.root?.leftChild?.rightChild?.isRed = false
    tree.root?.rightChild?.leftChild?.isRed = false
    tree.root?.leftChild?.leftChild?.isRed = false
    tree.remove(4)
    assertEquals(3, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(0, tree.root?.leftChild?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.leftChild?.isRed)
    assertEquals(1, tree.root?.leftChild?.key)
    assertEquals(true, tree.root?.leftChild?.isRed)
    assertEquals(2, tree.root?.leftChild?.rightChild?.key)
    assertEquals(false, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(6, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(5, tree.root?.rightChild?.leftChild?.key)
    assertEquals(true, tree.root?.rightChild?.leftChild?.isRed)
    assertEquals(listOf(0, 1, 2, 3, 5, 6), tree.getKeys())
  }

  @Test
  fun `remove black node without child and black left brother without child with black parent should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
    tree.root?.rightChild?.rightChild?.isRed = false
    tree.root?.leftChild?.rightChild?.isRed = false
    tree.root?.rightChild?.leftChild?.isRed = false
    tree.root?.leftChild?.leftChild?.isRed = false
    tree.remove(6)
    assertEquals(3, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(0, tree.root?.leftChild?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.leftChild?.isRed)
    assertEquals(1, tree.root?.leftChild?.key)
    assertEquals(true, tree.root?.leftChild?.isRed)
    assertEquals(2, tree.root?.leftChild?.rightChild?.key)
    assertEquals(false, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(4, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(5, tree.root?.rightChild?.rightChild?.key)
    assertEquals(true, tree.root?.rightChild?.rightChild?.isRed)
    assertEquals(listOf(0, 1, 2, 3, 4, 5), tree.getKeys())
  }

  @Test
  fun `remove black node without child and black brother without child with red parent should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(4 to 1, 2 to 4, 3 to 2, 0 to 3, 6 to 6, 5 to 5, 1 to 6))
    tree.root?.rightChild?.isRed = true
    tree.root?.leftChild?.isRed = true
    tree.root?.rightChild?.rightChild?.isRed = false
    tree.root?.leftChild?.rightChild?.isRed = false
    tree.root?.rightChild?.leftChild?.isRed = false
    tree.root?.leftChild?.leftChild?.isRed = false
    tree.remove(4)
    assertEquals(3, tree.root?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(0, tree.root?.leftChild?.leftChild?.key)
    assertEquals(false, tree.root?.leftChild?.leftChild?.isRed)
    assertEquals(1, tree.root?.leftChild?.key)
    assertEquals(true, tree.root?.leftChild?.isRed)
    assertEquals(2, tree.root?.leftChild?.rightChild?.key)
    assertEquals(false, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(5, tree.root?.rightChild?.key)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(6, tree.root?.rightChild?.rightChild?.key)
    assertEquals(true, tree.root?.rightChild?.rightChild?.isRed)
    assertEquals(listOf(0, 1, 2, 3, 5, 6), tree.getKeys())
  }

  @Test
  fun `remove black node without child and right red brother should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(15 to 1, 10 to 2, 25 to 3, 20 to 4, 30 to 5))
    tree.root?.rightChild?.isRed = true
    tree.root?.rightChild?.rightChild?.isRed = false
    tree.root?.rightChild?.leftChild?.isRed = false
    tree.remove(10)
    assertEquals(25, tree.root?.key)
    assertEquals(30, tree.root?.rightChild?.key)
    assertEquals(15, tree.root?.leftChild?.key)
    assertEquals(20, tree.root?.leftChild?.rightChild?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(true, tree.root?.leftChild?.rightChild?.isRed)
    assertEquals(listOf(15, 20, 25, 30), tree.getKeys())
  }

  @Test
  fun `remove black node without child and left red brother should correctly`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(20 to 1, 10 to 2, 25 to 3, 15 to 4, 5 to 5))
    tree.root?.leftChild?.isRed = true
    tree.root?.leftChild?.rightChild?.isRed = false
    tree.root?.leftChild?.leftChild?.isRed = false
    tree.remove(25)
    assertEquals(10, tree.root?.key)
    assertEquals(5, tree.root?.leftChild?.key)
    assertEquals(20, tree.root?.rightChild?.key)
    assertEquals(15, tree.root?.rightChild?.leftChild?.key)
    assertEquals(false, tree.root?.isRed)
    assertEquals(false, tree.root?.leftChild?.isRed)
    assertEquals(false, tree.root?.rightChild?.isRed)
    assertEquals(true, tree.root?.rightChild?.leftChild?.isRed)
    assertEquals(listOf(5, 10, 15, 20), tree.getKeys())
  }
  //endregion
}

class TreeSearchWithRedBlackTest {
  //region SEARCH TEST
  @Test
  fun `search a non-existing key should return null`() {
    val tree = RedBlackSearchTree<Int, Int>()
    assertEquals(null, tree.search(10))
  }

  @Test
  fun `search a existing key should return value greater than this key`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
    assertEquals(1, tree.search(10))
    assertEquals(2, tree.search(15))
    assertEquals(3, tree.search(5))
  }
  //endregion

  //region REMOVE SEVERAL KEYS TEST
  @Test
  fun `remove several keys should succeed)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(20 to 1, 5 to 2, 15 to 3, 25 to 4, 30 to 5))
    tree.remove(listOf(20, 5, 30))
    assertEquals(listOf(15, 25), tree.getKeys())
  }
  //endregion

  //region INSERT SEVERAL KEYS TEST
  @Test
  fun `insert several keys should succeed)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(20 to 1, 5 to 2, 15 to 3, 25 to 4, 30 to 5))
    assertEquals(listOf(5, 15, 20, 25, 30), tree.getKeys())
  }
  //endregion

  //region GET VALUES(KEYS) TEST
  @Test
  fun `getKeys(Value) with an empty tree it should return empty list`() {
    val tree = RedBlackSearchTree<Int, Int>()
    assertEquals(listOf<Int>(), tree.getValues())
    assertEquals(listOf<Int>(), tree.getKeys())
  }

  @Test
  fun `getKeys(Value) with a non-empty tree should return a list with keys(value)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
    assertEquals(listOf(3, 1, 2), tree.getValues())
    assertEquals(listOf(5, 10, 15), tree.getKeys())
  }
  //endregion

  //region GET MIN(MAX) TEST
  @Test
  fun `getMin(Max) with a empty tree should return a null)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    assertEquals(null, tree.getMinKey())
    assertEquals(null, tree.getMaxKey())
  }

  @Test
  fun `getMin(Max) with a non-empty tree should return a min(max) key)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(10 to 1, 15 to 2, 5 to 3))
    assertEquals(5, tree.getMinKey())
    assertEquals(15, tree.getMaxKey())
  }
  //endregion

  //region MIN NODE TEST
  @Test
  fun `getMinNode should return a min node)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(10 to 1, 15 to 2, 5 to 3, 12 to 4, 20 to 5))
    tree.remove(10)
    assertEquals(listOf(5, 12, 15, 20), tree.getKeys())
  }
  //endregion

  //region REPLACE TEST
  @Test
  fun `replace value should succeed)`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(20 to 1, 5 to 2, 15 to 3))
    tree.replaceValue(5, 4)
    assertEquals(4, tree.search(5))
  }

  @Test
  fun `replace a non-existing key should throw NoSuchElementException`() {
    val tree = RedBlackSearchTree<Int, Int>()
    assertFailsWith<NoSuchElementException> { tree.replaceValue(10, 1) }
    tree.insert(10, 5)
    assertFailsWith<NoSuchElementException> { tree.replaceValue(20, 2) }
  }
  //endregion

  //region CLEAN TEST
  @Test
  fun `clean should succeed`() {
    val tree = RedBlackSearchTree<Int, Int>()
    tree.insert(listOf(0 to 1, 1 to 2, 10 to 3, 15 to 4, 100 to 5, 5 to 6))
    tree.clean()
    assertEquals(listOf<Int>(), tree.getKeys())
  }
  //endregion
}