class MapPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    code = ''
    if store_field? and required?
      code << "    private final #{generic_type} #{name} = #{map_init_code}\n"
      code << "            .parent(this).index(#{field_conf.field_index_const_name}).key(#{field_conf.store_name_const_name});\n"
    else
      code << "    private @Nullable #{generic_type} #{name};\n"
    end
    code
  end

  def generic_type
    @generic_type ||= parse_generic_type
  end

  def key_type
    @key_type ||= parse_key_type
  end

  def value_type
    @value_type ||= parse_value_type
  end

  def single_value_type
    @single_value_type ||= parse_single_value_type
  end

  private
  def parse_generic_type
    if field_conf.type == 'object'
      "DefaultMapModel<#{key_type}, #{value_type}>"
    else
      "SingleValueMapModel<#{key_type}, #{value_type}>"
    end
  end

  def parse_key_type
    case field_conf.key
    when 'int'
      'Integer'
    when 'long'
      'Long'
    when 'string'
      'String'
    else
      raise ArgumentError, "Unsupported key type: #{field_conf.key}"
    end
  end

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
    when 'datetime'
      'LocalDateTime'
    when 'object'
      "#{field_conf.model}"
    else
      raise ArgumentError, "Unsupported value type: #{value}"
    end
  end

  def map_init_code
    if field_conf.value == 'object'
      case key_type
      when 'Integer'
        "DefaultMapModel.integerKeysMap(#{value_type}::new)"
      when 'Long'
        "DefaultMapModel.longKeysMap(#{value_type}::new)"
      when 'String'
        "DefaultMapModel.stringKeysMap(#{value_type}::new)"
      else
        raise ArgumentError, "Unsupported key type: #{key_type}"
      end
    else
      case key_type
      when 'Integer'
        "SingleValueMapModel.integerKeysMap(#{single_value_type})"
      when 'Long'
        "SingleValueMapModel.longKeysMap(#{single_value_type})"
      when 'String'
        "SingleValueMapModel.stringKeysMap(#{single_value_type})"
      else
        raise ArgumentError, "Unsupported key type: #{key_type}"
      end
    end
  end

  def parse_single_value_type
    case value_type
    when 'Integer'
      'SingleValues.integer()'
    when 'Long'
      'SingleValues.longValue()'
    when 'Double'
      'SingleValues.doubleValue()'
    when 'BigDecimal'
      'SingleValues.bigDecimal()'
    when 'String'
      'SingleValues.string()'
    when 'LocalDateTime'
      'SingleValues.localDateTime()'
    else
      raise ArgumentError, "Unsupported single value type: #{value_type}"
    end
  end

end
