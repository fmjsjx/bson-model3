require_relative '../property_generator'
require 'json'


class ObjectIdPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      "    private ObjectId #{name};\n"
    else
      "    private @Nullable ObjectId #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public ObjectId #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable ObjectId #{field_conf.getter_name}() {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(ObjectId #{name}) {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(@Nullable ObjectId #{name}) {\n"
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
