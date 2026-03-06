require_relative '../property_generator'


class BooleanPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value? and default_value_code != 'false'
        "    private boolean #{name} = #{default_value_code};\n"
      else
        "    private boolean #{name};\n"
      end
    else
      "    private @Nullable Boolean #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public boolean #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable Boolean #{field_conf.getter_name}() {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(boolean #{name}) {\n"
      if store_field?
        code << "        if (#{name} != this.#{name}) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    else
      code << "    public void set#{field_conf.camel_case_name}(@Nullable Boolean #{name}) {\n"
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
  def default_value_code
    case field_conf.default.downcase
    when 'true', '1', 'yes', 'y', 'on'
      'true'
    else
      'false'
    end
  end

end
