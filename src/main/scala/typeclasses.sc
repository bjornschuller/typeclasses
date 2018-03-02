/**
  * The trait describes the functionality of a specific 'data type'.
  * This trait is stateless and it just contains this function(s), which you always have to implement for every type
  * that can behave like a Showable.
  */
trait Showable[A] {
  def show(a: A): String
}

/**
  * In the companion object we specify the implementations for each TYPE that we want to treat as a Showable
  */
object Showable {

  /**
    * marking the Showable implicits allows the compiler to look for matching implicits when we call: show(20)
    * Making it implicit to avoid calling it explicitly (reducing boilerplate)
    */
  def show[A](a: A)(implicit s: Showable[A]): String = s.show(a)

  /**
    * This is the implicit implementation for a Showable[Int] type.
    * making it implicit to avoid  calling it explicitly (reducing boilerplate)
    */
  implicit val showInt: Showable[Int] = new Showable[Int]{
    override def show(int: Int): String = s"int $int"
    }
}

Showable.show(20) //compiler finds the showInt functionality since it is marked as implicit
//Showable.show(true) //gives compile error since we didn't create an implicit for it.


/**
  * SYNTASTIC SUGAR FOR THE SAME, AS ABOVE:
  */
trait Showable2[A] {
  def show(a: A): String
}

object Showable2 {

  // specified in the apply method tells that in every Showable2 Type an implicit Showable2[A] should be in scope
  //  def apply[A](implicit s: Showable2[A]) = s

  /**
    * making the show method context bound by: [A: Showable2]
    * Context bound syntax is used to declare that for some type A, there is an implicit value of type Showable2[A]
    * so def show[A: Showable2](a: A) is the same as def show[A](a: A)(implicit s: Showable[A])
    * The only difference is that we now define the implicit inside of our method instead of via
    * the parameter list.
    */
  def show[A: Showable2](a: A) = {
    val s = implicitly[Showable2[A]]
    s.show(a)
  }
  //same as: Showable2[A: Showable2].show(a)

  implicit val showInt: Showable2[Int] = new Showable2[Int]{
    override def show(int: Int): String = s"int $int"
  }

  implicit val showString: Showable2[String] = new Showable2[String]{
    override def show(str: String): String = s"string $str"
  }

    //allows us to call 20.show instead of show(20)
    implicit class ShowOps[A: Showable2](a: A) {
      val s = implicitly[Showable2[A]]
      def show = s.show(a)
    }

  def print() = (show(20), 20.show, show("hello"), "hello".show)

}

Showable2.print()

/**
  * SYNTASTIC SUGAR FOR THE SAME, AS ABOVE:
  */
trait Showable3[A] {
  def show(a: A): String
}

object Showable3 {

  /**
    * specified in the apply method tells that in every Showable3 Type an implicit Showable3[A] should be in scope
    * In this case, you don't have to do implicitly[Showable2[A]] inside the show method.
    * Reduces some of the boilerplate of the context bound implementation
    */
  def apply[A](implicit s: Showable3[A]) = s

  /**
    * The show function still uses a Context bound syntax.
    * Only the implicit is defined in the apply method. So this is a shortCut for implicitly[Showable2[A]]
    */
  def show[A: Showable3](a: A) = Showable3.apply[A].show(a) //same as: Showable3[A: Showable3].show(a)

  implicit val showInt: Showable3[Int] = new Showable3[Int]{
     override def show(int: Int): String = s"int $int"
  }

  implicit val showString: Showable3[String] = new Showable3[String]{
      override def show(str: String): String = s"string $str"
    }

  /**
    *  Allows us to call 20.show instead of show(20)
    *  Next to this, because we defined the implicit in the apply method it
    *  allows the compiler to find the matching implicits
    */
  implicit class ShowOps[A: Showable3](a: A) {
    def show = Showable3[A].show(a)
  }

  def print() = (show(20), 20.show, show("hello"), "hello".show)

}

Showable3.print()