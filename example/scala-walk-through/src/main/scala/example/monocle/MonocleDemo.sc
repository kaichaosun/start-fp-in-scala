case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

val employee = Employee(
  "john",
  Company("awesome inc",
    Address("london",
      Street(23, "high street")
    )
  )
)

employee.copy(
  company = employee.company.copy(
    address = employee.company.address.copy(
      street = employee.company.address.street.copy(
        name = employee.company.address.street.name.capitalize // luckily capitalize exists
      )
    )
  )
)

import monocle.Lens
import monocle.macros.GenLens

val streetName: Lens[Employee, String] = GenLens[Employee](_.company.address.street.name)

streetName.modify(_ + "_modify")(employee)


