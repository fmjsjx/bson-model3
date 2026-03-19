require_relative '../property_generator'


module StoreData

  class MapPropertyGenerator < PropertyGenerator

    attr_reader :value_type

    def initialize(config, model_conf, field_conf)
      super(config, model_conf, field_conf)
      @value_type = parse_value_type
    end

    def generate_field_declaration_code
      code = generate_field_json_annotations_code
      if required?
        code << "        private Map<String, #{value_type}> #{@field_conf.name};\n"
      else
        code << "        private @Nullable Map<String, #{value_type}> #{@field_conf.name};\n"
      end
    end

    def generate_getter_code
      code = ''
      if required?
        code << "        public Map<String, #{value_type}> #{@field_conf.getter_name}() {\n"
      else
        code << "        public @Nullable Map<String, #{value_type}> #{@field_conf.getter_name}() {\n"
      end
      code << "            return #{@field_conf.name};\n"
      code << "        }\n"
    end

    def generate_setter_code
      code = ''
      if required?
        code << "        public void #{@field_conf.setter_name}(Map<String, #{value_type}> #{@field_conf.name}) {\n"
      else
        code << "        public void #{@field_conf.setter_name}(@Nullable Map<String, #{value_type}> #{@field_conf.name}) {\n"
      end
      code << "            this.#{@field_conf.name} = #{@field_conf.name};\n"
      code << "        }\n"
    end

    private
    def parse_value_type
      value = @field_conf.value
      case value
      when 'int'
        'Integer'
      when 'long', 'datetime'
        'Long'
      when 'double'
        'Double'
      when 'decimal'
        'BigDecimal'
      when 'string'
        'String'
      when 'object'
        "#{@field_conf.model}.#{@field_conf.model}StoreData"
      else
        raise ArgumentError, "Unsupported value type: #{value}"
      end
    end
  end

end
