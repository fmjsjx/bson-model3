require_relative '../load_field_generator'
require_relative '../properties/map_property_generator'


class LoadMapGenerator < LoadFieldGenerator

  attr_reader :property_generator

  def initialize(config, model_conf, field_conf)
    super(config, model_conf, field_conf)
    @property_generator = MapPropertyGenerator.new(config, model_conf, field_conf)
  end

    def generate_load_optional_field_code
    code = ''
    code << "        BsonUtil.documentValue(src, #{field_conf.store_name_const_name}).ifPresentOrElse(\n"
    code << "                it -> {\n"
    code << "                    var #{temp_field_name} = this.#{field_conf.name};\n"
    code << "                    if (#{temp_field_name} != null) {\n"
    code << "                        #{temp_field_name}.unbind()\n"
    code << "                    }\n"
    code << "                    this.#{field_conf.name} = #{@property_generator.generate_init_code}\n"
    code << "                            .parent(this).index(#{field_conf.field_index_const_name}).key(#{field_conf.store_name_const_name})\n"
    code << "                            .load(it);\n"
    code << "                },\n"
    code << "                () -> {\n"
    code << "                    var #{temp_field_name} = this.#{field_conf.name};\n"
    code << "                    if (#{temp_field_name} != null) {\n"
    code << "                        #{temp_field_name}.unbind()\n"
    code << "                        this.#{field_conf.name} = null;\n"
    code << "                    }\n"
    code << "                }\n"
    code << "        );\n"
  end

end
