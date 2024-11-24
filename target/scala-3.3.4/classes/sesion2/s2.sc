
val l = List(1,2,3,4)
/**Ejercicio 1: Encuentra el número mayor en un array */
def maxNumber(arr: List[Int]): Int = arr.max

println(maxNumber(l))

/** Ejercicio 2: Cuenta las vocales en una cadena */
def countVowels(str: String): Int = str.count(x => "aeiouAEIOU".contains(x))


/**Ejercicio 3: Reversa una cadena */
def reverseString(str: String): String = str.reverse
reverseString("hola que tal")

/**Ejercicio 5: Verifica si un número es palíndromo*/
def isPalindrome(num: Int): Boolean = num.toString == num.toString.reverse
isPalindrome(11811)

/** Ejercicio 8: Identifica los números pares del 1 al 20 */
def evenNumbers(range: Range): Array[Int] = range.filter(_ % 2 == 0).toArray

evenNumbers(1 to 20)



/** ENCAPSULAMIENTO */

  class BankAccount(var balance: Double) {
    def depositar(amount: Double): Unit = {
      if (amount > 0) balance += amount
    }

    def retirar(amount: Double): Unit = {
      if (amount > 0 && amount <= balance) balance -= amount
      else println("No tienes tanto disponible")
    }

    def getBalance: Double = balance
  }

val account = new BankAccount(1000)
account.depositar(500)
account.retirar(20000)

println(account.getBalance)



def keisimo(n:Int, lista: List[Int]):Int = {
  if (n < 0 || n >= lista.length) throw new NoSuchElementException("La lista no es tan larga o el número es inválido")
  else lista match {
    case head :: Nil  => {
      head
    }
    case head :: tail if (n == tail.length) => {
      head
    } 
    case head :: tail if (n != tail.length) => {
      keisimo(n, tail)
    }
  }

}
keisimo(2, List(1,2,3,4))