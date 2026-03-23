class DeletedDataGenerator

  attr_reader :config, :model_conf, :field_confs

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields.filter do |field_conf|
      field_conf.display_field?
    end.filter do |field_conf|
      field_conf.store_field? or field_conf.virtual?
    end.filter do |field_conf|
      case field_conf.type
      when 'object', 'map'
        true
      else
        not field_conf.required?
      end
    end
  end

  def generate_append_deleted_data_code
    code = ''
    code << "    @Override\n"
    code << "    protected void appendDeletedData(Map<String, ? super Object> data) {\n"
    code << "        var changedFields = this.changedFields;\n"
    field_confs.each do |field_conf|
      code << generate_append_deleted_field_data_code(field_conf)
    end
    code << "    }\n"
  end
  
  private
  def generate_append_deleted_field_data_code(field_conf)
    code = ''
    case field_conf.type
    when 'object', 'map'
      if field_conf.required?
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
        code << "            var _#{field_conf.name} = #{field_conf.getter_name}().toDeleted();\n"
        code << "            if (_#{field_conf.name} != null) {\n"
        code << "                data.put(#{field_conf.display_name_const_name}, _#{field_conf.name});\n"
        code << "            }\n"
        code << "        }\n"
      else
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
        code << "            var _#{field_conf.name} = #{field_conf.getter_name}();\n"
        code << "            if (_#{field_conf.name} == null) {\n"
        code << "                data.put(#{field_conf.display_name_const_name}, BsonModelConstants.DELETED_VALUE);\n"
        code << "            } else {\n"
        code << "                var _#{field_conf.name}Deleted = _#{field_conf.name}.toDeleted();\n"
        code << "                if (_#{field_conf.name}Deleted != null) {\n"
        code << "                    data.put(#{field_conf.display_name_const_name}, _#{field_conf.name}Deleted);\n"
        code << "                }\n"
        code << "            }\n"
        code << "        }\n"
      end
    else
      code << "        if (changedFields.get(#{field_conf.field_index_const_name}) && #{field_conf.getter_name}() == null) {\n"
      code << "            data.put(#{field_conf.display_name_const_name}, BsonModelConstants.DELETED_VALUE);\n"
      code << "        }\n"
    end
  end

end
