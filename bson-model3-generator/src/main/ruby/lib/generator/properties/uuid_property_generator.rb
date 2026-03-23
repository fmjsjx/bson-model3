require_relative '../property_generator'


class UuidPropertyGenerator < PropertyGenerator

  def initialize(config, model_conf, field_conf)
    super(config, model_conf, field_conf)
  end

  def generate_field_declaration_code
    if required?
      "    private UUID #{name};\n"
    else
      "    private @Nullable UUID #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public UUID #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable UUID #{field_conf.getter_name}() {\n"
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
      code << "    public void #{field_conf.setter_name}(UUID #{name}) {\n"
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
      code << "    public void #{field_conf.setter_name}(@Nullable UUID #{name}) {\n"
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

end
