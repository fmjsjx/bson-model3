module StoreData

  class SimplePropertyGenerator < PropertyGenerator

    attr_reader :primitive_type, :boxed_type

    def initialize(config, model_conf, field_conf, primitive_type, boxed_type=nil)
      super(config, model_conf, field_conf)
      @primitive_type = primitive_type
      @boxed_type = boxed_type || primitive_type
    end

    def generate_field_declaration_code
      code = generate_field_json_annotations_code
      if required?
        code << "        private #{@primitive_type} #{@field_conf.name};\n"
      else
        code << "        private @Nullable #{@boxed_type} #{@field_conf.name};\n"
      end
    end

    def generate_getter_code
      code = ''
      if required?
        code << "        public #{@primitive_type} #{@field_conf.getter_name}() {\n"
      else
        code << "        public @Nullable #{@boxed_type} #{@field_conf.getter_name}() {\n"
      end
      code << "            return #{@field_conf.name};\n"
      code << "        }\n"
    end

    def generate_setter_code
      code = ''
      if required?
        code << "        public void #{@field_conf.setter_name}(#{@primitive_type} #{@field_conf.name}) {\n"
      else
        code << "        public void #{@field_conf.setter_name}(@Nullable #{@boxed_type} #{@field_conf.name}) {\n"
      end
      code << "            this.#{@field_conf.name} = #{@field_conf.name};\n"
      code << "        }\n"
    end

  end

end