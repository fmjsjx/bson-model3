class DeepCopyFromGenerator

  attr_reader :config, :model_conf, :field_confs

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields.select do |field_conf|
      field_conf.store_field?
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    public #{@model_conf.name} deepCopyFrom(#{@model_conf.name} src) {\n"
    @field_confs.each do |field_conf|
      code << generate_deep_copy_from_field_code(field_conf)
    end
    code << "        return this;\n"
    code << "    }\n"
  end

  private
  def generate_deep_copy_from_field_code(field_conf)
    code = ''
    case field_conf.type
    when 'object', 'map'
      if field_conf.required?
        code << "        #{field_conf.getter_name}().deepCopyFrom(src.#{field_conf.getter_name}());\n"
      else
        code << "        var _#{field_conf.name} = #{field_conf.getter_name}();\n"
        code << "        if (_#{field_conf.name} != null) {\n"
        code << "            _#{field_conf.name}.detach();\n"
        code << "            this.#{field_conf.name} = null;\n"
        code << "        }\n"
        code << "        _#{field_conf.name} = src.#{field_conf.getter_name}();\n"
        code << "        if (_#{field_conf.name} != null) {\n"
        code << "            this.#{field_conf.name} = _#{field_conf.name}.deepCopy()\n"
        code << "                    .parent(this).index(#{field_conf.field_index_const_name}).key(#{field_conf.store_name_const_name});\n"
        code << "        }\n"
      end
    when 'std-list'
      if field_conf.required?
        code << "        #{field_conf.name} = new ArrayList<>(src.#{field_conf.getter_name}());\n"
      else
        code << "        var _#{field_conf.name} = src.#{field_conf.getter_name}();\n"
        code << "        if (_#{field_conf.name} != null) {\n"
        code << "            #{field_conf.name} = new ArrayList<>(_#{field_conf.name});\n"
        code << "        } else {\n"
        code << "            #{field_conf.name} = null;\n"
        code << "        }\n"
      end
    else
      code << "        #{field_conf.name} = src.#{field_conf.getter_name}();\n"
    end
  end
  
end
