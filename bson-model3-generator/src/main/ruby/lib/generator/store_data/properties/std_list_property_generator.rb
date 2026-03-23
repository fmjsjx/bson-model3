require_relative '../property_generator'


module StoreData

  class StdListPropertyGenerator < PropertyGenerator

    attr_reader :key_type, :value_type

    def initialize(config, model_conf, field_conf)
      super(config, model_conf, field_conf)
    end

    def generate_field_declaration_code
      code = generate_field_json_annotations_code
      if required?
        code << "        private List<@Nullable #{value_type}> #{@field_conf.name};\n"
      else
        code << "        private @Nullable List<@Nullable #{value_type}> #{@field_conf.name};\n"
      end
    end

    def generate_getter_code
      code = ''
      if required?
        code << "        public List<@Nullable #{value_type}> #{@field_conf.getter_name}() {\n"
      else
        code << "        public @Nullable List<@Nullable #{value_type}> #{@field_conf.getter_name}() {\n"
      end
      code << "            return #{@field_conf.name};\n"
      code << "        }\n"
    end

    def generate_setter_code
      code = ''
      if required?
        code << "        public void #{@field_conf.setter_name}(List<@Nullable #{value_type}> #{@field_conf.name}) {\n"
      else
        code << "        public void #{@field_conf.setter_name}(@Nullable List<@Nullable #{value_type}> #{@field_conf.name}) {\n"
      end
      code << "            this.#{@field_conf.name} = #{@field_conf.name};\n"
      code << "        }\n"
    end

    def value_type
      @value_type ||= parse_value_type
    end

    private
    def parse_value_type
      value = @field_conf.value
      case value
      when 'int', 'date', 'time'
        'Integer'
      when 'long', 'datetime'
        'Long'
      when 'double'
        'Double'
      when 'decimal'
        'BigDecimal'
      when 'string', 'object-id', 'uuid'
        'String'
      when 'object'
        "#{@field_conf.model}.#{@field_conf.model}StoreData"
      else
        raise ArgumentError, "Unsupported value type: #{value}"
      end
    end

  end

end
