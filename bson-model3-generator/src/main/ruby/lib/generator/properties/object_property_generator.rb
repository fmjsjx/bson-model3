class ObjectPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    code = ''
    if store_field? and required?
      code << "    private final #{field_conf.model} #{name} = new #{field_conf.model}()\n"
      code << "            .parent(this).index(#{field_conf.field_index_const_name}).key(#{field_conf.store_name_const_name});\n"
    else
      code << "    private @Nullable #{field_conf.model} #{name};\n"
    end
  end

  def no_setter?
    field_conf.virtual? or (store_field? and required?)
  end

  def generate_getter_code
    code = ''
    if store_field? and required?
      code << "    public #{field_conf.model} #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable #{field_conf.model} #{field_conf.getter_name}() {\n"
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
    code << "    public void set#{field_conf.camel_case_name}(@Nullable #{field_conf.model} #{name}) {\n"
    if store_field?
      code << "        if (!Objects.equals(this.#{name}, #{name})) {\n"
      code << "            if (#{name} != null) {\n"
      code << "                #{name}.ensureDetached();\n"
      code << "                if (this.#{name} != null) {\n"
      code << "                    this.#{name}.detach();\n"
      code << "                }\n"
      code << "                this.#{name} = #{name}.parent(this).index(#{field_conf.field_index_const_name}).key(#{field_conf.store_name_const_name}).fullUpdate();\n"
      code << "            } else {\n"
      code << "                this.#{name}.detach();\n"
      code << "                this.#{name} = null;\n"
      code << "            }\n"
      code << "            #{field_changed_code}\n"
      code << "        }\n"
    else
      code << "        this.#{name} = #{name};\n"
    end
    code << "    }\n"
  end

end
