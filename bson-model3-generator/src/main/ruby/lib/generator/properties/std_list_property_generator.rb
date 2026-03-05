class StdListPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      "    private List<@Nullable #{value_type}> #{name} = List.of();\n"
    else
      "    private @Nullable List<@Nullable #{value_type}> #{name};\n"
    end
  end

  def value_type
    @value_type = parse_value_type
  end

  private
  def parse_value_type
    value = field_conf.value
    case value
    when 'int'
      'Integer'
    when 'long'
      'Long'
    when 'double'
      'Double'
    when 'decimal'
      'BigDecimal'
    when 'string'
      'String'
    when 'date'
      'LocalDate'
    when 'time'
      'LocalTime'
    when 'datetime'
      'LocalDateTime'
    when 'object-id'
      'ObjectId'
    when 'uuid'
      'UUID'
    when 'object'
      "#{field_conf.model}"
    else
      raise ArgumentError, "Unsupported value type: #{value}"
    end
  end

end
