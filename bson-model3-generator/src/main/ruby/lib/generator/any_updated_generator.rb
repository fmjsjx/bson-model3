class AnyUpdatedGenerator

  attr_reader :config, :model_conf, :field_confs

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields.filter { |field_conf| field_conf.store_field? }
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    public boolean anyUpdated() {\n"
    if @field_confs.empty?
      code << "        return false;\n"
    else
      code << "        if (isFullUpdate()) {\n"
      code << "            return true;\n"
      code << "        }\n"
      code << "        var changedFields = this.changedFields;\n"
      code << "        if (changedFields.isEmpty()) {\n"
      code << "            return false;\n"
      code << "        }\n"
      @field_confs.each do |field_conf|
        code << generate_if_field_changed_code(field_conf)
      end
      code << "        return false;\n"
    end
    code << "    }\n"
  end

  private
  def generate_if_field_changed_code(field_conf)
    code = ''
    if field_conf.required?
      case field_conf.type
      when 'object', 'map'
        code << "        if (changedFields.get(#{field_conf.field_index_const_name}) && #{field_conf.getter_name}().anyUpdated()) {\n"
      else
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
      end
      code << "            return true;\n"
      code << "        }\n"
    else
      case field_conf.type
      when 'object', 'map'
        code << "        if (changedFields.get(#{field_conf.field_index_const_name})) {\n"
        code << "            var _#{field_conf.name} = #{field_conf.getter_name}();\n"
        code << "            return _#{field_conf.name} != null && _#{field_conf.name}.anyUpdated();\n"
        code << "        }\n"
      else
        code << "        if (changedFields.get(#{field_conf.field_index_const_name}) && #{field_conf.getter_name}() != null) {\n"
        code << "            return true;\n"
        code << "        }\n"
      end
    end
  end

end
