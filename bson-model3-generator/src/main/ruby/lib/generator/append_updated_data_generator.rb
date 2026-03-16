require_relative 'append_updated_field_data_generator'


class AppendUpdatedDataGenerator

  attr_reader :config, :model_conf, :append_updated_field_data_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @append_updated_field_data_generators = model_conf.fields.filter do |field_conf|
      not (field_conf.readonly? or field_conf.transient? or field_conf.hidden?)
    end.map do |field_conf|
      AppendUpdatedFieldDataGenerator.from(field_conf, config, model_conf)
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    protected void appendUpdatedData(Map<String, ? super Object> data) {\n"
    code << "        var changedFields = this.changedFields;\n"
    code << "        if (changedFields.isEmpty()) {\n"
    code << "            return;\n"
    code << "        }\n"
    @append_updated_field_data_generators.each do |append_updated_field_data_generator|
      code << append_updated_field_data_generator.generate
    end
    code << "    }\n"
  end

end
