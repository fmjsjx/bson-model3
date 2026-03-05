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

end
