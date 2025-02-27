package de.simplyroba.suite.budgets.service.converter

interface Converter<S, T> {

  /**
   * Convert the source object of type {@code S} to target type {@code T}.
   *
   * @param source the source object to convert, which must be an instance of {@code S}
   * @return the converted object, which must be an instance of {@code T}
   * @throws IllegalArgumentException if the source cannot be converted to the desired target type
   */
  fun convert(source: S): T
}
