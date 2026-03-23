require_relative '../property_generator'


module StoreData

  class ObjectPropertyGenerator < PropertyGenerator

    attr_reader :type

    def initialize(config, model_conf, field_conf)
      super(config, model_conf, field_conf)
      @type = parse_type(field_conf)
    end

    def generate_field_declaration_code
      code = generate_field_json_annotations_code
      code << "        private #{@type} #{@field_conf.name};\n"
    end

    def generate_getter_code
      code = ''
      code << "        public #{@type} #{@field_conf.getter_name}() {\n"
      code << "            return #{@field_conf.name};\n"
      code << "        }\n"
    end

    def generate_setter_code
      code = ''
      code << "        public void #{@field_conf.setter_name}(#{@type} #{@field_conf.name}) {\n"
      code << "            this.#{@field_conf.name} = #{@field_conf.name};\n"
      code << "        }\n"
    end

    private
    def parse_type(field_conf)
      if field_conf.required?
        "#{field_conf.model}.#{field_conf.model}StoreData"
      else
        "#{field_conf.model}.@Nullable #{field_conf.model}StoreData"
      end
    end

  end

end