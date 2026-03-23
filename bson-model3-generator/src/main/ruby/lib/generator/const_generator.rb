class ConstGenerator

  attr_reader :model_conf, :const_conf

  def initialize(model_conf, const_conf)
    @model_conf = model_conf
    @const_conf = const_conf
  end

  def generate
    name = @const_conf.name
    type, type_1 = @const_conf.type.split(/\s+/)
    value = @const_conf.value
    case type
    when 'int', 'long', 'double', 'boolean'
      "    public static final #{type} #{name} = #{value};\n"
    when 'decimal'
      "    public static final BigDecimal #{name} = #{value};\n"
    when 'string'
      "    public static final String #{name} = \"#{value}\";\n"
    when 'date'
      "    public static final LocalDate #{name} = #{value};\n"
    when 'time'
      "    public static final LocalTime #{name} = #{value};\n"
    when 'datetime'
      "    public static final LocalDateTime #{name} = #{value};\n"
    when 'list'
      if type_1.nil?
        raise ArgumentError, "Missing value type for const type list"
      end
      case type_1
      when 'int'
        "    public static final List<Integer> #{name} = #{value};\n"
      when 'long'
        "    public static final List<Long> #{name} = #{value};\n"
      when 'double'
        "    public static final List<Double> #{name} = #{value};\n"
      when 'string'
        "    public static final List<String> #{name} = #{value};\n"
      else
        raise ArgumentError, "Unsupported value type #{type_1} for const type list"
      end
    when 'set'
      if type_1.nil?
        raise ArgumentError, "Missing value type for const type set"
      end
      case type_1
      when 'int'
        "    public static final Set<Integer> #{name} = #{value};\n"
      when 'long'
        "    public static final Set<Long> #{name} = #{value};\n"
      when 'double'
        "    public static final Set<Double> #{name} = #{value};\n"
      when 'string'
        "    public static final Set<String> #{name} = #{value};\n"
      else
        raise ArgumentError, "Unsupported value type #{type_1} for const type set"
      end
    else
      raise ArgumentError, "Unsupported const type #{type}"
    end
  end

end
