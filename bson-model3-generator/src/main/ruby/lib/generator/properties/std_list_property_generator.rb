require_relative '../property_generator'


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

  def generate_getter_code
    code = ''
    if required?
      code << "    public List<@Nullable #{value_type}> #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable List<@Nullable #{value_type}> #{field_conf.getter_name}() {\n"
    end
    if virtual?
      code << "#{virtual_code}\n"
    else
      code << "        return #{name};\n"
    end
    code << "    }\n"
  end

  def generate_setter_code
    code = ''
    if required?
      code << "    public void set#{field_conf.camel_case_name}(List<@Nullable #{value_type}> #{name}) {\n"
      if store_field?
        code << "        if (!#{name}.equals(this.#{name})) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    else
      code << "    public void set#{field_conf.camel_case_name}(@Nullable List<@Nullable #{value_type}> #{name}) {\n"
      if store_field?
        code << "        if (!Objects.equals(this.#{name}, #{name})) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    end
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
