class DeletedGenerator

  attr_reader :config, :model_conf, :field_confs

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields.filter do |field_conf|
      field_conf.store_field?
    end.filter do |field_conf|
      case field_conf.type
      when 'object', 'map'
        true
      else
        not field_conf.required?
      end
    end
  end

  def generate_any_deleted_code
    code = ''
    code << "    @Override\n"
    code << "    public boolean anyDeleted() {\n"
    code << "        if (isFullUpdate()) {\n"
    code << "            return false;\n"
    code << "        }\n"
    code << "        var changedFields = this.changedFields;\n"
    code << "        if (changedFields.isEmpty()) {\n"
    code << "            return false;\n"
    code << "        }\n"
    field_confs.each do |field_conf|
      code << generate_check_field_deleted_code(field_conf)
    end
    code << "        return false;\n"
    code << "    }\n"
  end

  def generate_deleted_size_code
    code = ''
    code << "    @Override\n"
    code << "    public int deletedSize() {\n"
    code << "        if (isFullUpdate()) {\n"
    code << "            return 0;\n"
    code << "        }\n"
    code << "        var changedFields = this.changedFields;\n"
    code << "        if (changedFields.isEmpty()) {\n"
    code << "            return 0;\n"
    code << "        }\n"
    code << "        var __size = 0;\n"
    field_confs.each do |field_conf|
      code << generate_increase_field_deleted_code(field_conf)
    end
    code << "        return __size;\n"
    code << "    }\n"
  end

  private
  def generate_check_field_deleted_code(field_conf)
    code = ''
    case field_conf.type
    when 'object', 'map'
      if field_conf.required?
        code << "        if (changedFields.get(#{field_conf.field_index_const_name}) && #{field_conf.getter_name}().anyDeleted()) {\n"
        code << "            return true;\n"
        code << "        }\n"
      else
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
        code << "            var _#{field_conf.name} = #{field_conf.getter_name}();\n"
        code << "            if (_#{field_conf.name} == null || _#{field_conf.name}.anyDeleted()) {\n"
        code << "                return true;\n"
        code << "            }\n"
        code << "        }\n"
      end
    else
      code << "        if (changedFields.get(#{field_conf.field_index_const_name}) && #{field_conf.getter_name}() == null) {\n"
      code << "            return true;\n"
      code << "        }\n"
    end
  end

  def generate_increase_field_deleted_code(field_conf)
    code = ''
    case field_conf.type
    when 'object', 'map'
      if field_conf.required?
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
        code << "            __size += #{field_conf.getter_name}().deletedSize();\n"
        code << "        }\n"
      else
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
        code << "            var _#{field_conf.name} = #{field_conf.getter_name}();\n"
        code << "            if (_#{field_conf.name} == null) {\n"
        code << "                __size++;\n"
        code << "            } else {\n"
        code << "                __size += _#{field_conf.name}.deletedSize();\n"
        code << "            }\n"
        code << "        }\n"
      end
    else
      code << "        if (changedFields.get(#{field_conf.field_index_const_name}) && #{field_conf.getter_name}() == null) {\n"
      code << "            __size++;\n"
      code << "        }\n"
    end
  end

end
