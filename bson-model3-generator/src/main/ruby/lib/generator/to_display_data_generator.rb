require_relative 'to_display_field_data_generator'


class ToDisplayDataGenerator

  attr_reader :config, :model_conf, :to_display_field_data_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @to_display_field_data_generators = model_conf.fields.filter do |field_conf|
      not field_conf.hidden?
    end.map do |field_conf|
      ToDisplayFieldDataGenerator.from(field_conf, config, model_conf)
    end
  end

  def generate
    code =''
    code << "    @Override\n"
    code << "    public Map<String, ?> toDisplayData() {\n"
    code << "        var _displayData = new LinkedHashMap<String, Object>();\n"
    @to_display_field_data_generators.each do |to_display_field_data_generator|
      code << to_display_field_data_generator.generate
    end
    code << "        return _displayData;\n"
    code << "    }\n"
  end

end