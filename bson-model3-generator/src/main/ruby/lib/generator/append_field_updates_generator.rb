require_relative 'append_field_update_generator'


class AppendFieldUpdatesGenerator

  attr_reader :config, :model_conf, :append_field_update_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @append_field_update_generators = model_conf.fields.filter do |field_conf|
      field_conf.store_field?
    end.map do |field_conf|
      AppendFieldUpdateGenerator.from(config, model_conf, field_conf)
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    protected void appendFieldUpdates(List<Bson> updates) {\n"
    code << "        var changedFields = this.changedFields;\n"
    code << "        if (changedFields.isEmpty()) {\n"
    code << "            return;\n"
    code << "        }\n"
    @append_field_update_generators.each do |append_field_update_generator|
      code << append_field_update_generator.generate
    end
    code << "    }\n"
  end

end
